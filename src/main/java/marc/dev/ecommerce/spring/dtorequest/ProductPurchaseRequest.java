package marc.dev.ecommerce.spring.dtorequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPurchaseRequest(
        @NotNull(message = "ProductId is mandatory")
        Long productId,
        @NotNull(message = "Product Name is required")
        String productName,
        @NotNull(message = "ProductQuantity is mandatory")
        Long quantity,
        @Positive(message = "productPrice should be positive")
        @NotNull(message = "productPrice should be precised")
        double productPrice,
        String image

) {

}
