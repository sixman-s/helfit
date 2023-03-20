package sixman.helfit.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import sixman.helfit.security.support.XssProtectionSupport;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class XssProtectionConfig {
    private final ObjectMapper mapper;

    @Bean
    public MappingJackson2HttpMessageConverter characterEscapeConverter() {
        ObjectMapper objectMapper = mapper.copy();
        objectMapper.getFactory().setCharacterEscapes(new XssProtectionSupport());

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
