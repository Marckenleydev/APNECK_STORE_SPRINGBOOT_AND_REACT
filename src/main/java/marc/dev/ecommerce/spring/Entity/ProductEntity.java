package marc.dev.ecommerce.spring.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@JsonInclude(NON_DEFAULT)
public class ProductEntity extends Auditable{

    private String name;
    @Column(name = "description", length = 2000)
    private String description;
    private Long availableQuantity;
    private double price;
    private String brand;
    private String rate;

    private List<String> imageUrls= new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ImageProductEntity> productImages = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CategoryEntity category;

}
