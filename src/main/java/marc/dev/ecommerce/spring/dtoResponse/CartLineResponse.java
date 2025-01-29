package marc.dev.ecommerce.spring.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartLineResponse {
    private Long productId;
    private String productName;
    private String description;
    private double price;
    private String imageUrl;
    private Long quantity;
    private Long availableQuantity;
    private String brand;
}
