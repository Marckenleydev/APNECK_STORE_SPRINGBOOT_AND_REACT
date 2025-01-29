package marc.dev.ecommerce.spring.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
@JsonInclude(NON_DEFAULT)
public class AddressEntity extends Auditable{
    private String street;
    private String houseNumber;
    private String zipCode;
    @OneToOne(mappedBy = "address")
    private UserEntity customer;
}
