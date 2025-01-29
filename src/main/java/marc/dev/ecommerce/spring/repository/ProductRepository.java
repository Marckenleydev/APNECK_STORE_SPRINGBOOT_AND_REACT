package marc.dev.ecommerce.spring.repository;


import marc.dev.ecommerce.spring.Entity.CategoryEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByIdInOrderById(List<Long> productIds);
    Optional<ProductEntity> findByReferenceId(String referenceId);

    List<ProductEntity> findProductByCategory(CategoryEntity category);
}

