package marc.dev.ecommerce.spring.repository;

import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLineRepository extends JpaRepository<CartLineEntity,Long> {
}
