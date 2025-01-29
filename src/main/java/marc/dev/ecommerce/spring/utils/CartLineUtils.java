package marc.dev.ecommerce.spring.utils;


import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import marc.dev.ecommerce.spring.dtoResponse.CartLineResponse;
import org.springframework.stereotype.Service;

@Service
public class CartLineUtils {
    public CartLineEntity toCartLine(ProductEntity product, CartEntity cartEntity) {
        String imageUrl = product.getImageUrls() != null && !product.getImageUrls().isEmpty()
                ? product.getImageUrls().get(0)
                : null; // Default to null or a placeholder if no images are present

        return CartLineEntity.builder()
                .cart(cartEntity)
                .quantity(1L)
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(imageUrl)
                .brand(product.getBrand())
                .availableQuantity(product.getAvailableQuantity())
                .build();
    }


    public CartLineResponse toCartLineResponse(CartLineEntity cartLine) {
        return  new CartLineResponse(
                cartLine.getProductId(),
                cartLine.getProductName(),
                cartLine.getDescription(),
                cartLine.getPrice(),
                cartLine.getImageUrl(),
                cartLine.getQuantity(),
                cartLine.getAvailableQuantity(),
                cartLine.getBrand()
        );



    }
}
