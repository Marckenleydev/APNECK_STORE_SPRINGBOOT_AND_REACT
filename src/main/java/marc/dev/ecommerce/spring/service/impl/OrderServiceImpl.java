package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.dtoResponse.OrderResponse;
import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderLineRequest;
import marc.dev.ecommerce.spring.dtorequest.OrderRequest;
import marc.dev.ecommerce.spring.dtorequest.ProductPurchaseRequest;
import marc.dev.ecommerce.spring.dtorequest.StripeRequest;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.repository.CartRepository;
import marc.dev.ecommerce.spring.repository.OrderRepository;
import marc.dev.ecommerce.spring.repository.UserRepository;
import marc.dev.ecommerce.spring.service.*;
import marc.dev.ecommerce.spring.utils.OrderUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderLineService orderLineService;
    private final CartRepository cartRepository;
    private final OrderUtils orderUtils;
    private final  StripeService stripeService;
    @Override
    public StripeResponse createOrderAndCheckout( String userId) {
        var customer = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ApiException("Customer not found"));
        var cart = cartRepository.findByCustomer(customer);

        // Check if the product purchase quantity is available
        productService.purchaseProduct(cart.getCartLines());
        // Persist order
        var order = this.orderRepository.save(orderUtils.toOrder(cart, customer));

        // Persist order lines
        for (CartLineEntity cartLine : cart.getCartLines()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            cartLine.getProductId(),
                            cartLine.getProductName(),
                            cartLine.getQuantity(),
                            cartLine.getPrice(),
                            cartLine.getImageUrl()
                    ),
                    order
            );
        }

        String shippingAddress = String.format("%s, %s, %s",
                customer.getAddress().getStreet(),
                customer.getAddress().getHouseNumber(),
                customer.getAddress().getZipCode());

        // Send order confirmation email
        emailService.sendHtmlConfirmationOrderMail(
                customer.getFirstName(),
                order.getReferenceId(),
                order.getCreatedAt(),
                shippingAddress,
                customer.getEmail()
        );

        // Prepare Stripe request data
        StripeRequest stripeRequest = prepareStripeRequest(cart, customer);
        // Clear customer's cart
        cart.getCartLines().clear();
        cart.setTotalProduct(0L);
        cart.setTotalAmount(0.0);
        cartRepository.save(cart);
        // Checkout products with Stripe
        return stripeService.checkoutProducts(stripeRequest);
    }

    private StripeRequest prepareStripeRequest(CartEntity cart, UserEntity customer) {
        StripeRequest stripeRequest = new StripeRequest();
        stripeRequest.setName(customer.getFirstName());
        stripeRequest.setCurrency("USD");

        // Populate product details
        List<String> productNames = new ArrayList<>();
        List<String> productImages = new ArrayList<>();
        List<Double> productPrices = new ArrayList<>();
        List<Long> productQuantities = new ArrayList<>();

        for (CartLineEntity cartLine : cart.getCartLines()) {
            productNames.add(cartLine.getProductName());
            productImages.add(cartLine.getImageUrl());
            productPrices.add(cartLine.getPrice()); // Assuming price is part of ProductPurchaseRequest
            productQuantities.add((long) cartLine.getQuantity());
        }

        stripeRequest.setProductNames(productNames);
        stripeRequest.setProductImages(productImages);
        stripeRequest.setProductPrices(productPrices);
        stripeRequest.setProductQuantities(productQuantities);

        return stripeRequest;
    }


    @Override
    public List<OrderResponse> getOrders() {

        var orders = orderRepository.findAll();
      return   orders.stream().map(orderUtils::fromOrder).collect(Collectors.toList());
    }

    @Override
    public OrderResponse findById(Long orderId) {
        return null;
    }
}
