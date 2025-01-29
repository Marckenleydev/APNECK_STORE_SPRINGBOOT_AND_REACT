package marc.dev.ecommerce.spring.Entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.EAGER;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name="orderLines")
public class OrderLineEntity extends Auditable{

    @ManyToOne(fetch = EAGER)
    @JoinTable(
            name = "orderLines_order",
            joinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "orderline_id", referencedColumnName = "id"
            )
    )
    private OrderEntity order;
    private Long productId;
    private String productName;
    private Long quantity;
    private String image;
}
