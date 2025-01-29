package marc.dev.ecommerce.spring;

import marc.dev.ecommerce.spring.Entity.RoleEntity;
import marc.dev.ecommerce.spring.domain.RequestContext;
import marc.dev.ecommerce.spring.enumeration.Authority;
import marc.dev.ecommerce.spring.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			/* RequestContext.setUserId(0L);
			var customerRole = new RoleEntity();
			customerRole.setName(Authority.CUSTOMER.name());
			customerRole.setAuthorities(Authority.CUSTOMER);
			roleRepository.save(customerRole);



			var superAdminRole = new RoleEntity();
			superAdminRole.setName(Authority.SUPER_ADMIN.name());
			superAdminRole.setAuthorities(Authority.SUPER_ADMIN);
			roleRepository.save(superAdminRole);

			var manager = new RoleEntity();
			manager.setName(Authority.MANAGER.name());
			manager.setAuthorities(Authority.MANAGER);
			roleRepository.save(manager);

			RequestContext.start(); */
		};
	}
}
