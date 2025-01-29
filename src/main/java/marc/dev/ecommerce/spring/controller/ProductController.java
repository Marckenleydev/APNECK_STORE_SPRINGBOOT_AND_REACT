package marc.dev.ecommerce.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.domain.Response;
import marc.dev.ecommerce.spring.dtoResponse.ProductPurchaseResponse;
import marc.dev.ecommerce.spring.dtoResponse.ProductResponse;
import marc.dev.ecommerce.spring.dtorequest.ProductRequest;
import marc.dev.ecommerce.spring.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static marc.dev.ecommerce.spring.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;



    @PostMapping("/{categoryId}")
    public ResponseEntity<Response> createProduct(
            @ModelAttribute @Valid ProductRequest productRequest,
            @PathVariable("categoryId") Long categoryId,
            HttpServletRequest request) throws IOException {

        var product = productService.createProduct(categoryId, productRequest);

        return ResponseEntity.ok()
                .body(getResponse(request, Map.of("product", product), "Product created successfully", HttpStatus.OK));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(
            @RequestBody List<CartLineEntity> request
    ){
        return  ResponseEntity.ok(productService.purchaseProduct(request));
    }

    @GetMapping("{product-id}")
    public  ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Long productId){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @DeleteMapping("{product-id}")
    public  ResponseEntity<Response> findById(@PathVariable("product-id") Long productId,HttpServletRequest request){
   productService.deleteProduct(productId);

        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Product deleted successfully.", OK));
    }

    @GetMapping("/referenceId/{referenceId}")
    public  ResponseEntity<ProductResponse> findByReferenceId(@PathVariable("referenceId") String referenceId){
        return ResponseEntity.ok(productService.findByReferenceId(referenceId));
    }

    @GetMapping
    public  ResponseEntity<List<ProductResponse>> findAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/category/{categoryId}")
    public  ResponseEntity<List<ProductResponse>> findProductsByCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(productService.findProductByCategory(categoryId));
    }
}
