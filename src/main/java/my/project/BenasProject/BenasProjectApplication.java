package my.project.BenasProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BenasProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenasProjectApplication.class, args);
    }
}
