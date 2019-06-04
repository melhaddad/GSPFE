package ma.ilem.inventorymanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    @Bean
    org.springframework.data.domain.AuditorAware<String> auditorProvider() {
        return new AuditorAware();
    }
}
