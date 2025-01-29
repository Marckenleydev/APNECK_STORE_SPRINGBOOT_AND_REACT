package marc.dev.ecommerce.spring.dtorequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import marc.dev.ecommerce.spring.enumeration.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @Positive(message = "Order amount should be positive")
        double amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,

        @NotEmpty(message = "You should at least purchase one product")
        List<ProductPurchaseRequest> products
) {
}
