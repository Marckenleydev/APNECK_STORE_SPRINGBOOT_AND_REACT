package marc.dev.ecommerce.spring.repository;


import marc.dev.ecommerce.spring.Entity.OrderLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLineEntity, Long> {
    List<OrderLineEntity> findAllByOrderId(Long orderId);
}
