package sixman.helfit.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import sixman.helfit.security.filter.XssProtectionFilter;
import sixman.helfit.security.support.XssProtectionSupport;

import java.util.HashMap;


// @Configuration
@RequiredArgsConstructor
@Slf4j
public class XssProtectionConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<XssProtectionFilter> XssFilter(){
        FilterRegistrationBean<XssProtectionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new XssProtectionFilter());
        registrationBean.addUrlPatterns("/db/h2/*");
        registrationBean.setInitParameters(new HashMap<>() {{
            put("excludePatterns", "/db/h2/*");
        }});
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter characterEscapeConverter() {
        ObjectMapper objectMapper = this.objectMapper.copy();
        objectMapper.getFactory().setCharacterEscapes(new XssProtectionSupport());

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
