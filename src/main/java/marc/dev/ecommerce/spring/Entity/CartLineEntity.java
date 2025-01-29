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
@Table(name="cartLines")
public class CartLineEntity extends Auditable{
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
    private Long productId;
    private String productName;
    @Column(name = "description", length = 2000)
    private String description;
    private double price;
    private String imageUrl;
    private String brand;
    private Long quantity;
    private Long availableQuantity;
}
