package marc.dev.ecommerce.spring.dtoResponse;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Long productId,
        String ProductName,
        String description,
        double price,
        Long quantity
) {
}
