package marc.dev.ecommerce.spring.service;

import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.dtoResponse.CartResponse;
import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderRequest;

public interface CartService {
    public CartEntity addProductToCart(Long productId, String userId);
    public void removeProductToCart(Long productId, String userId);

    public CartResponse getUSerCart(String userId);
}
