package marc.dev.ecommerce.spring.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static marc.dev.ecommerce.spring.utils.EmailUtils.getEmailMessage;
import static marc.dev.ecommerce.spring.utils.EmailUtils.getResetPasswordMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Reset Password Request";
    private static final String ORDER_CONFIRMATION = "Oder Confirmation Email";
    private final JavaMailSender sender;
    private static final String UTF_8_ENCODING = "UTF-8";
    private String EMAIL_TEMPLATE ="emailtemplate";
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendHtmlConfirmationOrderMail(String customerName,
                                              String orderId,
                                              LocalDateTime OrderDate,
                                              String shippingAddress, String to) {
        try{
            Context context = new Context();
            context.setVariables(Map.of(
                    "customerName",customerName,
                    "orderID",orderId,
                    "Order Date", OrderDate,
                    "shippingAddress", shippingAddress));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(ORDER_CONFIRMATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);


            sender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }
    }

    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getEmailMessage(name,host,token));
            sender.send(message);

        }catch(Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("Unable to send email");
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String name, String email, String token) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(PASSWORD_RESET_REQUEST);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getResetPasswordMessage(name, host, token));
            sender.send(message);
        }catch(Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("Unable to send email");
        }

    }


    private MimeMessage getMimeMessage() {
        return sender.createMimeMessage();
    }

}
