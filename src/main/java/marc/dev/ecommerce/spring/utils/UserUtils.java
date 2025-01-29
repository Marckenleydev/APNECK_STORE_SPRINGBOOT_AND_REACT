package marc.dev.ecommerce.spring.utils;



import marc.dev.ecommerce.spring.Entity.AddressEntity;
import marc.dev.ecommerce.spring.Entity.CredentialEntity;
import marc.dev.ecommerce.spring.Entity.RoleEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.dto.Address;
import marc.dev.ecommerce.spring.dto.User;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

import static marc.dev.ecommerce.spring.constant.Constants.NINETY_DAYS;
import static org.apache.commons.lang3.StringUtils.EMPTY;
public class UserUtils {
    public static UserEntity createUserEntity(String firstName, String lastName, String email, RoleEntity role, AddressEntity addressEntity) {

        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(now())
                .accountNonLocked(true)
                .enabled(true)
                .loginAttempts(0)
                .phone(EMPTY)
                .imageUrl("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                .address(addressEntity)
                .role(role)
                .build();
    }





    public static User fromUserEntity(UserEntity userEntity,RoleEntity role,CredentialEntity credentialEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.setLastLogin(userEntity.getLastLogin().toString());
        user.setCreatedAt(userEntity.getCreatedAt().toString());
        user.setUpdatedAt(userEntity.getUpdatedAt().toString());
        user.setRole(role.getName());

        // Map the AddressEntity to Address DTO
        if (userEntity.getAddress() != null) {
            Address address = new Address();
            AddressEntity addressEntity = userEntity.getAddress();
            address.setStreet(addressEntity.getStreet());
            address.setHouseNumber(addressEntity.getHouseNumber());
            address.setZipCode(addressEntity.getZipCode());

            // Convert LocalDateTime to String
            if (addressEntity.getCreatedAt() != null) {
                address.setCreatedAt(addressEntity.getCreatedAt().toString());
            }
            if (addressEntity.getUpdatedAt() != null) {
                address.setUpdatedAt(addressEntity.getUpdatedAt().toString());
            }

            user.setAddress(address);
        }

        // Handle other fields
        if (userEntity.getLastLogin() != null) {
            user.setLastLogin(userEntity.getLastLogin().toString());
        }
        if (userEntity.getCreatedAt() != null) {
            user.setCreatedAt(userEntity.getCreatedAt().toString());
        }
        if (userEntity.getUpdatedAt() != null) {
            user.setUpdatedAt(userEntity.getUpdatedAt().toString());
        }

        return user;
    }


    public static boolean isCredentialsNonExpired(CredentialEntity credentialEntity) {
        return credentialEntity.getUpdatedAt().plusDays(NINETY_DAYS).isAfter(now());
    }



}

