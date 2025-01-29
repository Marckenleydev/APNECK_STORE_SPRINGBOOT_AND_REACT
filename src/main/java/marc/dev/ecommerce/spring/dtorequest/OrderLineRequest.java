package marc.dev.ecommerce.spring.dtorequest;

import java.math.BigDecimal;

public record OrderLineRequest(

        Long productId,

        String productName,

        Long quantity,
        double price,
        String image) {
}