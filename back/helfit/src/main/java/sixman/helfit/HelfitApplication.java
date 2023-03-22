package sixman.helfit;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.properties.CorsProperties;

@EnableJpaAuditing
@EnableBatchProcessing
@EnableConfigurationProperties({
	CorsProperties.class,
	AppProperties.class
})
@SpringBootApplication
public class HelfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelfitApplication.class, args);
	}

}
