package marc.dev.ecommerce.spring.securtity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static marc.dev.ecommerce.spring.constant.Constants.STRENGTH;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(STRENGTH);
    }
}
