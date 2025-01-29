package marc.dev.ecommerce.spring.service;


import java.time.LocalDateTime;

public interface EmailService {
    void sendNewAccountEmail(String name, String email, String token);
    void sendPasswordResetEmail(String name, String email, String token);
    void sendHtmlConfirmationOrderMail(String patientName,
                                       String orderId,
                                       LocalDateTime OrderDate,
                                       String shippingAddress, String to);
}