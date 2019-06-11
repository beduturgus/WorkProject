package my.project.BenasProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;



@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableResourceServer
public class BenasProjectApplication {

	public static void main(String[] args) {
//		System.setProperty("spring.config.name", "BenasProject");
		SpringApplication.run(BenasProjectApplication.class, args);
	}

}
