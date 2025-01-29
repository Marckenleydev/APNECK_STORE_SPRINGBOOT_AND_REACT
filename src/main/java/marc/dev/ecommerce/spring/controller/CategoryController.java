package marc.dev.ecommerce.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.domain.Response;
import marc.dev.ecommerce.spring.dtorequest.ImageRequest;
import marc.dev.ecommerce.spring.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static marc.dev.ecommerce.spring.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody CategoryEntity categoryRequest, HttpServletRequest request){
        var  category = categoryService.saveCategory(categoryRequest);
        return ResponseEntity.ok().body(getResponse(request, Map.of("Category", category), "Category created Successfully", OK));
    }
    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
