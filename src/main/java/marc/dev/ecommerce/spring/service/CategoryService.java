package marc.dev.ecommerce.spring.service;

import marc.dev.ecommerce.spring.Entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    public String saveCategory(CategoryEntity categoryRequest);
    public List<CategoryEntity> getCategories();
}
