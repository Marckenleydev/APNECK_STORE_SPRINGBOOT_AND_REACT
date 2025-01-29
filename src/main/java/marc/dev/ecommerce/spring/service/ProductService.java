package marc.dev.ecommerce.spring.service;



import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.dtoResponse.ProductPurchaseResponse;
import marc.dev.ecommerce.spring.dtoResponse.ProductResponse;
import marc.dev.ecommerce.spring.dtorequest.ProductPurchaseRequest;
import marc.dev.ecommerce.spring.dtorequest.ProductRequest;

import java.util.List;

public interface ProductService {
    public String createProduct(Long categoryId, ProductRequest productRequest);
    public List<ProductPurchaseResponse> purchaseProduct(List<CartLineEntity> request);
    public void deleteProduct(Long productId);
    public ProductResponse findById(Long productId);
    public ProductResponse findByReferenceId(String referenceId);
    public List<ProductResponse> findAll();
    public List<ProductResponse>findProductByCategory(Long CategoryId);
}
