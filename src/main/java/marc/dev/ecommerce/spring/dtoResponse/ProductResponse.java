package marc.dev.ecommerce.spring.dtoResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record ProductResponse(
        String referenceId,
        Long id,
        String name,
        String description,
        String brand,
        String rate,
        double availableQuantity,

        double price,
        List<String> imageUrls
) {
}
