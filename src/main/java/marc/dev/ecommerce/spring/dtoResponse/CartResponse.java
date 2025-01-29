package marc.dev.ecommerce.spring.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long id;
    private String reference;
    private double amount;
    private Long totalProduct;
    private String customerName;

    List<CartLineResponse> products;
}
