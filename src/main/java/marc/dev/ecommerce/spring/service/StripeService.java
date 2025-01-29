package marc.dev.ecommerce.spring.service;


import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.StripeRequest;

public interface StripeService {
    public StripeResponse checkoutProducts(StripeRequest stripeRequest);

}
