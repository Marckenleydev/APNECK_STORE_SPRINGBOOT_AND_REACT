package marc.dev.ecommerce.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.domain.Response;
import marc.dev.ecommerce.spring.dto.User;
import marc.dev.ecommerce.spring.dtoResponse.CartResponse;
import marc.dev.ecommerce.spring.dtorequest.CartRequest;
import marc.dev.ecommerce.spring.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static marc.dev.ecommerce.spring.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/addproduct/{productId}")
    public ResponseEntity<Response> addProductCart(@AuthenticationPrincipal User userPrincipal, @PathVariable("productId") Long productId , HttpServletRequest request){
        System.out.println(userPrincipal.getUserId());
        cartService.addProductToCart( productId,userPrincipal.getUserId());

        return ResponseEntity.ok().body(getResponse(request, Map.of("Category",""), "Product added Successfully", OK));
    }

    @PostMapping("/removeproduct/{productId}")
    public ResponseEntity<Response> removeProductCart(@AuthenticationPrincipal User userPrincipal, @PathVariable("productId") Long productId , HttpServletRequest request){

        cartService.removeProductToCart( productId,userPrincipal.getUserId());

        return ResponseEntity.ok().body(getResponse(request, Map.of("Category",""), "Product remove Successfully", OK));
    }

    @GetMapping("/myCart")
    public ResponseEntity<CartResponse> getUserCart(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request){
        return ResponseEntity.ok().body(cartService.getUSerCart(userPrincipal.getUserId()));
    }
}
