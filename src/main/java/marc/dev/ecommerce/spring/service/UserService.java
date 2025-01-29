package marc.dev.ecommerce.spring.service;


import marc.dev.ecommerce.spring.Entity.AddressEntity;
import marc.dev.ecommerce.spring.Entity.CredentialEntity;
import marc.dev.ecommerce.spring.Entity.RoleEntity;
import marc.dev.ecommerce.spring.dto.User;
import marc.dev.ecommerce.spring.enumeration.LoginType;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password, AddressEntity address);
    RoleEntity getRoleName(String name);
    void verifyAccount(String token);
    void updateLoginAttempt(String email, LoginType loginType);
    User getUserByUserId(String userId);
    User getUserByEmail(String email);
    CredentialEntity getUserCredentialById(Long id);


    void resetPassword(String email);

    User verifyPasswordKey(String key);

    void updatePassword(String userId, String newPassword, String confirmNewPassword);
    void updatePassword(String userId, String currentPassword, String newPassword, String confirmNewPassword);
    User updateUser(String userId, String firstName, String lastName, String email, String phone, String bio);

    void updateRole(String userId, String role);



    void toggleAccountLocked(String userId);

    void toggleAccountEnabled(String userId);

    void toggleCredentialExpired(String userId);

    String uploadPhoto(String userId, MultipartFile file);

    User getUserById(Long id);
}
