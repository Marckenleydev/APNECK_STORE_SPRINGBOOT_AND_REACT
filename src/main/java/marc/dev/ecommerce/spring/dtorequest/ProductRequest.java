package marc.dev.ecommerce.spring.dtorequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Product availableQuantity is must be greater than 0")
        Long availableQuantity,
        @NotNull(message = "Product Brand is required")
        String brand,
        String rate,
        @Positive(message = "Product price is must be greater than 0")
        double price,
        @NotNull(message = "Image is required")
        List<MultipartFile> images

) {

}
