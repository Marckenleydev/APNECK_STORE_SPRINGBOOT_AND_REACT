package marc.dev.ecommerce.spring.dtorequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StripeRequest {
    private List<String> productNames;
    private List<Double> productPrices; // Prices in USD
    private List<Long> productQuantities; // Quantities of each product
    private List<String> productImages; // Images of each product
    private String name; // Customer name
    private String currency; // E.g., USD

}
