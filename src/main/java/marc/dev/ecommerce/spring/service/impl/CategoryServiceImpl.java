package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.repository.CategoryRepository;
import marc.dev.ecommerce.spring.repository.ProductRepository;
import marc.dev.ecommerce.spring.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public String saveCategory(CategoryEntity categoryRequest) {

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

        return category.getName();
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }
}
