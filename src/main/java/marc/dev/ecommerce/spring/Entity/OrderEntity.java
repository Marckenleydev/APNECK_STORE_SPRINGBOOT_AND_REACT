package marc.dev.ecommerce.spring.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import marc.dev.ecommerce.spring.enumeration.PaymentMethod;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@JsonInclude(NON_DEFAULT)
public class OrderEntity extends Auditable{

    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "order")
    private List<OrderLineEntity> OrderLines;

    @ManyToOne(fetch = LAZY)
    @JoinTable(
            name = "customers_order",
            joinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            )
    )
    private UserEntity customer;
}
