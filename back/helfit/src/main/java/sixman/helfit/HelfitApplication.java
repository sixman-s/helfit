package sixman.helfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sixman.helfit.security.config.properties.AppProperties;
import sixman.helfit.security.config.properties.CorsProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
	CorsProperties.class,
	AppProperties.class
})
public class HelfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelfitApplication.class, args);
	}

}
