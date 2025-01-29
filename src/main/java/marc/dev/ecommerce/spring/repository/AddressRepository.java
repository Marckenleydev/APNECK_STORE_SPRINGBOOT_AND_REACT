package marc.dev.ecommerce.spring.repository;

import marc.dev.ecommerce.spring.Entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
