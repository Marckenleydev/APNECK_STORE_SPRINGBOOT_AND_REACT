package marc.dev.ecommerce.spring.validation;


import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.exception.ApiException;

public class UserValidation {
    public static void verifyAccountStatus(UserEntity userEntity) {
        if(!userEntity.isEnabled()){
            throw  new ApiException("User is disabled");
        }
        if(!userEntity.isAccountNonLocked()){ throw  new ApiException("User is locked");}
    }
}
