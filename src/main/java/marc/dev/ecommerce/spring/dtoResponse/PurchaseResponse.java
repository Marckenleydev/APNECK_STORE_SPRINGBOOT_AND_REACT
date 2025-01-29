package marc.dev.ecommerce.spring.dtoResponse;

import java.math.BigDecimal;

public record PurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Long quantity
) {
}