package marc.dev.ecommerce.spring.dtorequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartRequest {
    @NotEmpty(message = "ProductId cannot be empty or null")
    private Long productId;
}
