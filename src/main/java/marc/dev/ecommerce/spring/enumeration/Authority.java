package marc.dev.ecommerce.spring.enumeration;


import static marc.dev.ecommerce.spring.constant.Constants.*;

public enum Authority {
    CUSTOMER(CUSTOMER_AUTHORITIES),
    SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES),
    MANAGER(MANAGER_AUTHORITIES);

    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}