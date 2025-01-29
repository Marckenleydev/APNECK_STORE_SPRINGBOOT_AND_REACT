package marc.dev.ecommerce.spring.repository;


import marc.dev.ecommerce.spring.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
