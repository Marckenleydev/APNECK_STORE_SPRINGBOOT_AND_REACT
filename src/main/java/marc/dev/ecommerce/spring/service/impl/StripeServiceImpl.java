package marc.dev.ecommerce.spring.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.StripeRequest;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class StripeServiceImpl implements StripeService {
    @Value("${stripe.secretKey}")
    private String secretKey;

    @Override
    public StripeResponse checkoutProducts(StripeRequest stripeRequest) {
        Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        // Create line items for each product
        for (int i = 0; i < stripeRequest.getProductNames().size(); i++) {
            String productName = stripeRequest.getProductNames().get(i);
            String productImage = stripeRequest.getProductImages().get(i);
            double productPrice = stripeRequest.getProductPrices().get(i);
            double productAmountInCents = productPrice * 100; // Convert to cents
            long productQuantity = stripeRequest.getProductQuantities().get(i);

            System.out.println(productPrice);

            // Build product data
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(productName)
                            .addImage(productImage)
                            .build();

            // Build price data
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(stripeRequest.getCurrency())
                            .setUnitAmount((long) productAmountInCents)
                            .setProductData(productData)
                            .build();

            // Build line item
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(productQuantity)
                    .setPriceData(priceData)
                    .build();

            lineItems.add(lineItem);
        }

        // Create Stripe session
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:8000/success")
                .setCancelUrl("https://localhost:8000/cancel")
                .addAllLineItem(lineItems)
                .build();

        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new ApiException("Failed to create Stripe session: " + e.getMessage());
        }

        return StripeResponse.builder()
                .Status("SUCCESS")
                .message("SESSION CREATED")
                .sessionID(session.getId())
                .sessionURL(session.getUrl())
                .build();
    }

}
