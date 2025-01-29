package marc.dev.ecommerce.spring.dto;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
    private String createdAt;
    private String updatedAt;
}
