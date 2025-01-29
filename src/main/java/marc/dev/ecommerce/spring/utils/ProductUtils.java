package marc.dev.ecommerce.spring.utils;

import lombok.Getter;
import lombok.Setter;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import marc.dev.ecommerce.spring.dtoResponse.ProductPurchaseResponse;
import marc.dev.ecommerce.spring.dtoResponse.ProductResponse;
import marc.dev.ecommerce.spring.dtorequest.ProductRequest;
import org.springframework.stereotype.Service;
import marc.dev.ecommerce.spring.Entity.ImageProductEntity;


import java.util.ArrayList;

@Service
@Getter
@Setter
public class ProductUtils {
    public ProductEntity toProduct(ProductRequest productRequest, CategoryEntity category){
        return  ProductEntity.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .brand(productRequest.brand())
                .rate(productRequest.rate())
                .price(productRequest.price())
                .availableQuantity(productRequest.availableQuantity())
                .category(category).build();
    }

    public ProductResponse toProductResponse(ProductEntity product){
        return  new ProductResponse(
                product.getReferenceId(),
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getRate(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getImageUrls() != null
                        ? new ArrayList<>(product.getImageUrls())
                        : new ArrayList<>());


              
    }

    public ProductPurchaseResponse toProductPurchaseResponse(ProductEntity product, Long quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),

                quantity
        );
    }
}
