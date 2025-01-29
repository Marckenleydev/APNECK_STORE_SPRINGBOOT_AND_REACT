package marc.dev.ecommerce.spring.repository;



import marc.dev.ecommerce.spring.Entity.ConfirmationEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    Optional<ConfirmationEntity> findByToken(String token);
    Optional<ConfirmationEntity> findByUserEntity(UserEntity userEntity);
}