package marc.dev.ecommerce.spring.dtoResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import marc.dev.ecommerce.spring.dtorequest.ProductPurchaseRequest;
import marc.dev.ecommerce.spring.enumeration.PaymentMethod;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderResponse(
        Long id,
        String reference,
        double amount,
        PaymentMethod paymentMethod,
        String customerName,

        List<OrderLineResponse> products
) {

}
