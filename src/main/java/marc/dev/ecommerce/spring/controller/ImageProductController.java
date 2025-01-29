package marc.dev.ecommerce.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marc.dev.ecommerce.spring.domain.Response;
import marc.dev.ecommerce.spring.dtorequest.ImageRequest;
import marc.dev.ecommerce.spring.dtorequest.ProductRequest;
import marc.dev.ecommerce.spring.service.ImageService;
import marc.dev.ecommerce.spring.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static marc.dev.ecommerce.spring.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/images_product")
@RequiredArgsConstructor
public class ImageProductController {
    private final ImageService imageService;

    @PostMapping("/{productId}")
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ImageRequest imageRequest,@PathVariable("productId") Long productId, HttpServletRequest request){
        var imageProduct = imageService.saveImage(imageRequest, productId);
        return ResponseEntity.ok().body(getResponse(request, Map.of("Image", imageProduct.getId()), "Image Product created Successfully", OK));
    }
}
