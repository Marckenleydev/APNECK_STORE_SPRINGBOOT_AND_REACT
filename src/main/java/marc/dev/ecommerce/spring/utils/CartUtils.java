package marc.dev.ecommerce.spring.utils;

import lombok.Getter;
import lombok.Setter;
import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.dtoResponse.CartLineResponse;
import marc.dev.ecommerce.spring.dtoResponse.CartResponse;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Getter
@Setter
public class CartUtils {
    public CartEntity toCart(Long totalAmount, UserEntity userEntity, CartLineEntity cartLineEntity) {
        List<CartLineEntity> cartLines = new ArrayList<>(); // Initialize the products list
        cartLines.add(cartLineEntity); // Add the cart line to the list

        return CartEntity.builder()
                .customer(userEntity)
                .cartLines(cartLines) // Set the products list
                .totalAmount(totalAmount)
                .build();
    }


    public CartResponse fromCart(CartEntity cart) {
        return new CartResponse(
                cart.getId(),
                cart.getReferenceId(),
                cart.getTotalAmount(),
                cart.getTotalProduct(),
                cart.getCustomer().getFirstName(),
                cart.getCartLines() != null ?
                        cart.getCartLines().stream()
                                .sorted((line1, line2) -> Long.compare(line1.getProductId(), line2.getProductId())) // Sort by productId
                                .map(cartLine -> new CartLineResponse(
                                        cartLine.getProductId(),
                                        cartLine.getProductName(),
                                        cartLine.getDescription(),
                                        cartLine.getPrice(),
                                        cartLine.getImageUrl(),
                                        cartLine.getQuantity(),
                                        cartLine.getAvailableQuantity(),
                                        cartLine.getBrand()
                                ))
                                .collect(Collectors.toList()) : new ArrayList<>()
        );
    }

}
