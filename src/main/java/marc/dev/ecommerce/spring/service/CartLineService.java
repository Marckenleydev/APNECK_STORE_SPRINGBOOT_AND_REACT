package marc.dev.ecommerce.spring.service;

import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;

public interface CartLineService {
    public CartLineEntity saveCartLine(ProductEntity product,CartEntity cartEntity);
    public void deleteCartLine(Long productId);
}
