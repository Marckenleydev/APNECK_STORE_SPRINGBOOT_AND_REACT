package marc.dev.ecommerce.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import marc.dev.ecommerce.spring.dto.User;
import marc.dev.ecommerce.spring.dtoResponse.OrderResponse;
import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderRequest;
import marc.dev.ecommerce.spring.service.OrderService;
import marc.dev.ecommerce.spring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> createOrder(@AuthenticationPrincipal User userPrincipal) {
        System.out.println("User: " + userPrincipal);
        var user = userService.getUserByUserId(userPrincipal.getUserId());
        if(user.getUserId()  != null){
            StripeResponse stripeResponse = orderService.createOrderAndCheckout( user.getUserId());
            return ResponseEntity.ok(stripeResponse);
        }
        return null;


    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrder(){
        return ResponseEntity.ok(orderService.getOrders());
    }


}
