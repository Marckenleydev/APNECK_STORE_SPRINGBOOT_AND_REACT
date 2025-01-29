package marc.dev.ecommerce.spring.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.Entity.*;
import marc.dev.ecommerce.spring.cache.CacheStore;
import marc.dev.ecommerce.spring.domain.RequestContext;
import marc.dev.ecommerce.spring.dto.User;
import marc.dev.ecommerce.spring.enumeration.Authority;
import marc.dev.ecommerce.spring.enumeration.LoginType;
import marc.dev.ecommerce.spring.event.UserEvent;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.repository.*;
import marc.dev.ecommerce.spring.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.time.LocalDateTime.now;
import static marc.dev.ecommerce.spring.constant.Constants.*;
import static marc.dev.ecommerce.spring.enumeration.EventType.REGISTRATION;
import static marc.dev.ecommerce.spring.enumeration.EventType.RESETPASSWORD;
import static marc.dev.ecommerce.spring.utils.UserUtils.createUserEntity;
import static marc.dev.ecommerce.spring.utils.UserUtils.fromUserEntity;
import static marc.dev.ecommerce.spring.validation.UserValidation.verifyAccountStatus;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder encoder;
    private final CacheStore<String, Integer> userCache;
    private final ApplicationEventPublisher publisher;
    private final CartRepository cartRepository;

    @Override
    public void createUser(String firstName, String lastName, String email, String password, AddressEntity address) {
        var user = userRepository.findByEmailIgnoreCase(email);

        var userAddress = addressRepository.save(address);
        if(user.isPresent()){
            throw new ApiException("Email already exists. Use a different email and try again");

        }
        var userEntity = userRepository.save(createNewUser(firstName, lastName, email, userAddress));
        var credentialEntity = new CredentialEntity(userEntity, encoder.encode(password));
        // Create and save an empty cart for the new user
        var cartEntity = CartEntity.builder()
                .cartLines(new ArrayList<>())
                .customer(userEntity)
                .totalAmount(0)
                .totalProduct(0L)
                .build();

        cartRepository.save(cartEntity);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntity, REGISTRATION, Map.of("key", confirmationEntity.getToken())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    @Override
    public void verifyAccount(String key) {
        var confirmationEntity = getUserConfirmation(key);
        var userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var userEntity = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntity.getId());
        switch (loginType) {
            case LOGIN_ATTEMPT -> {
                if (userCache.get(userEntity.getEmail()) == null) {
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNonLocked(true);
                }
                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(), userEntity.getLoginAttempts());
                if (userCache.get(userEntity.getEmail()) > 5) {
                  //  userEntity.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                userEntity.setAccountNonLocked(true);
                userEntity.setLoginAttempts(0);
                userEntity.setLastLogin(now());
                userCache.evict(userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);
    }

    @Override
    public User getUserByUserId(String userId) {
        var userEntity = userRepository.findUserByUserId(userId).orElseThrow(() -> new ApiException("User not found"));
        return fromUserEntity(userEntity, userEntity.getRole(), getUserCredentialById(userEntity.getId()));
    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity userEntity = getUserEntityByEmail(email);

        return fromUserEntity(userEntity, userEntity.getRole(), getUserCredentialById(userEntity.getId()));
    }

    @Override
    public CredentialEntity getUserCredentialById(Long userId) {
        var credentialById = credentialRepository.getCredentialByUserEntityId(userId);

        return credentialById.orElseThrow(() -> new ApiException("Unable to find user credential"));
    }


    @Override
    public void resetPassword(String email) {
        var user = getUserEntityByEmail(email);
        var confirmation = getUserConfirmation(user);

        if(confirmation != null) {
            publisher.publishEvent(new UserEvent(user, RESETPASSWORD, Map.of("key", confirmation.getToken())));
        }else {
            var confirmationEntity = new ConfirmationEntity(user);
            confirmationRepository.save(confirmationEntity);
            publisher.publishEvent(new UserEvent(user, RESETPASSWORD, Map.of("key", confirmationEntity.getToken())));
        }

    }

    @Override
    public User verifyPasswordKey(String key) {
        var confirmationEntity = getUserConfirmation(key);
        if(confirmationEntity == null) {
            throw new ApiException("Unable to find token");
        }    
        var userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        if(userEntity == null) {
            throw new ApiException("Incorrect Token");
        }
        verifyAccountStatus(userEntity);
        confirmationRepository.delete(confirmationEntity);
        return fromUserEntity(userEntity, userEntity.getRole(), getUserCredentialById(userEntity.getId()));
    }

    @Override
    public void updatePassword(String userId, String newPassword, String confirmNewPassword) {
        if(!confirmNewPassword.equals(newPassword)){throw new ApiException("Password don't match. Please try again");}
        var user = getUserByUserId(userId);
        var credential = getUserCredentialById(user.getId());
        credential.setPassword(encoder.encode(newPassword));
        credentialRepository.save(credential);
    }

    @Override
    public void updatePassword(String userId, String currentPassword, String newPassword, String confirmNewPassword) {
        if(!confirmNewPassword.equals(newPassword)){throw new ApiException("Password don't match. Please try again");}
        var user = getUserEntityByUserId(userId);
        verifyAccountStatus(user);

        var credential = getUserCredentialById(user.getId());
        if(!encoder.matches(currentPassword, credential.getPassword())){throw new ApiException("Existing passwords is incorrect. Please try again");}
        credential.setPassword(encoder.encode(newPassword));
        credentialRepository.save(credential);
    }

    @Override
    public User updateUser(String userId, String firstName, String lastName, String email, String phone, String bio) {
        var userEntity = getUserEntityByUserId(userId);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setPhone(phone);


        userRepository.save(userEntity);

        return fromUserEntity(userEntity, userEntity.getRole(), getUserCredentialById(userEntity.getId()));
    }

    @Override
    public void updateRole(String userId, String role) {
        var userEntity = getUserEntityByUserId(userId);
        userEntity.setRole(getRoleName(role));
        userRepository.save(userEntity);

    }



    @Override
    public void toggleAccountLocked(String userId) {
        var userEntity = getUserEntityByUserId(userId);
        userEntity.setAccountNonLocked(!userEntity.isAccountNonLocked());
        var credential = getUserCredentialById(userEntity.getId());
        credential.setUpdatedAt(LocalDateTime.of(1996, 7, 12,11,11));

//        if(credential.getUpdatedAt().plusDays(NINETY_DAYS).isAfter(now())){
//            credential.setUpdatedAt(now());
//        }else {
//            credential.setUpdatedAt(LocalDateTime.of(1996, 7, 12,11,11));
//        }

        userRepository.save(userEntity);

    }

    @Override
    public void toggleAccountEnabled(String userId) {
        var userEntity = getUserEntityByUserId(userId);
        userEntity.setEnabled(!userEntity.isEnabled());

        userRepository.save(userEntity);

    }

    @Override
    public void toggleCredentialExpired(String userId) {
        var userEntity = getUserEntityByUserId(userId);
        userEntity.setAccountNonLocked(!userEntity.isAccountNonLocked());

        userRepository.save(userEntity);

    }

    @Override
    public String uploadPhoto(String userId, MultipartFile file) {
        var userEntity = getUserEntityByUserId(userId);
        var photoUrl = photoFunction.apply(userId, file);
        userEntity.setImageUrl(photoUrl + "?timestamp=" + System.currentTimeMillis());
        userRepository.save(userEntity);
        return photoUrl;
    }

    @Override
    public User getUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(()-> new ApiException("User not found"));
        return fromUserEntity(userEntity, userEntity.getRole(), getUserCredentialById(userEntity.getId()));
    }

    public  final BiFunction<String, MultipartFile, String> photoFunction =(userId, file)->{
        var filename = userId + ".png";

        try {
            var fileStorageLocation = Paths.get(FILE_STORAGE).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)){Files.createDirectories(fileStorageLocation);}
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/user/image/" + filename).toUriString();
        }catch(Exception exception){
            throw new ApiException("unable to save image");
        }
    };







    public UserEntity getUserEntityByUserId(String userId) {
        var userByUserId = userRepository.findUserByUserId(userId);
        return userByUserId.orElseThrow(() -> new ApiException("User not found"));
    }

    private UserEntity getUserEntityById(Long id) {
        var userById = userRepository.findById(id);
        return userById.orElseThrow(() -> new ApiException("User not found"));
    }

    private UserEntity getUserEntityByEmail(String email) {
        var userByEmail = userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(() -> new ApiException("User not found"));
    }

    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByToken(key).orElseThrow(() -> new ApiException("Confirmation key not found"));
    }
    private ConfirmationEntity getUserConfirmation(UserEntity user) {
        return confirmationRepository.findByUserEntity(user).orElse(null);
    }
    private UserEntity createNewUser(String firstName, String lastName, String email, AddressEntity address) {
        var role = getRoleName(Authority.CUSTOMER.name());
        return createUserEntity(firstName, lastName, email, role, address);
    }
}