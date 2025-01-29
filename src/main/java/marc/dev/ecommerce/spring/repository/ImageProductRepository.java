package marc.dev.ecommerce.spring.repository;

import marc.dev.ecommerce.spring.Entity.ImageProductEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageProductRepository extends JpaRepository<ImageProductEntity,Long> {

    Optional<ImageProductEntity> findByProduct(ProductEntity product);
}
