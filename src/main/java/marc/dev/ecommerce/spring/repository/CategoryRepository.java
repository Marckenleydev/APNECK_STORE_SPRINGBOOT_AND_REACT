package marc.dev.ecommerce.spring.repository;

import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
