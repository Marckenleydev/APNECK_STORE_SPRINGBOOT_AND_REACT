package marc.dev.ecommerce.spring.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import marc.dev.ecommerce.spring.dtoResponse.ProductPurchaseResponse;
import marc.dev.ecommerce.spring.dtoResponse.ProductResponse;
import marc.dev.ecommerce.spring.dtorequest.ProductPurchaseRequest;
import marc.dev.ecommerce.spring.dtorequest.ProductRequest;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.repository.CategoryRepository;
import marc.dev.ecommerce.spring.repository.ProductRepository;
import marc.dev.ecommerce.spring.service.CloudinaryService;
import marc.dev.ecommerce.spring.service.ProductService;
import marc.dev.ecommerce.spring.utils.ProductUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductUtils productUtils;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    public String createProduct(Long categoryId, ProductRequest productRequest) {
        // Fetch the category
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException("Category not found"));

        // Upload images to Cloudinary
        List<String> imageUrls = productRequest.images().stream()
                .map(image -> cloudinaryService.uploadFile(image, "Apneck-product-Images"))
                .toList();
        System.out.println(imageUrls);

        if (imageUrls.isEmpty() || imageUrls.contains(null)) {
            throw new ApiException("Failed to upload images");
        }

        // Convert ProductRequest to ProductEntity and set category and images
        ProductEntity product = productUtils.toProduct(productRequest, category);
        product.setImageUrls(imageUrls);

        // Save product
        ProductEntity savedProduct = productRepository.save(product);

        // Return product name as a response
        return savedProduct.getName();
    }


    @Override
    public List<ProductPurchaseResponse> purchaseProduct(List<CartLineEntity> request) {
        var productIds = request.stream()
                .map(CartLineEntity::getProductId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ApiException("One or More Products doest not exists");
        }
        var storedRequest = request.stream().sorted(Comparator.comparing(CartLineEntity::getProductId))
                .toList();
        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.getQuantity()){
                throw new ApiException("Insufficient stock quantity for product with ID:: " + productRequest.getProductId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchaseProducts.add(productUtils.toProductPurchaseResponse(product, productRequest.getQuantity()));
        }

        return purchaseProducts;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponse findById(Long productId) {
        return  productRepository.findById(productId)
                .map(productUtils::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
    }
    @Override
    public ProductResponse findByReferenceId(String referenceId) {
        return  productRepository.findByReferenceId(referenceId)
                .map(productUtils::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + referenceId));
    }
    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .sorted((product1, product2) -> product1.getCreatedAt().compareTo(product2.getCreatedAt())) // Sort by createdAt
                .map(productUtils::toProductResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductResponse> findProductByCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ApiException("Category not found"));

        List<ProductEntity> products = productRepository.findProductByCategory(categoryEntity);
        if(products == null || products.isEmpty()){
            return  new ArrayList<>();
        }
        return products.stream().map(productUtils::toProductResponse).collect(Collectors.toList());
    }

}
