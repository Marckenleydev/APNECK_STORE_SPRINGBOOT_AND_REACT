package marc.dev.ecommerce.spring.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
@JsonInclude(NON_DEFAULT)
public class CartEntity extends Auditable{
    private double totalAmount;
    private Long totalProduct;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CartLineEntity> cartLines = new ArrayList<>();

    @ManyToOne(fetch = EAGER)
    @JoinTable(
            name = "customers_cart",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "cart_id", referencedColumnName = "id"
            )
    )
    private UserEntity customer;
}

