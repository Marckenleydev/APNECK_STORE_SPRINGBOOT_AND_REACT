package marc.dev.ecommerce.spring.repository;

import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByCustomer(UserEntity customer);
}
