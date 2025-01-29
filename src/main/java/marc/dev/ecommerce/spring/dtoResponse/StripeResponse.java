package marc.dev.ecommerce.spring.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeResponse {
    private String Status;
    private String message;

    private String sessionID;
    private String sessionURL;
}
