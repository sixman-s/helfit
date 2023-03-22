package sixman.helfit.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import sixman.helfit.security.filter.XssProtectionFilter;
import sixman.helfit.security.support.XssProtectionSupport;

import java.util.HashMap;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class XssProtectionConfig {
    private final ObjectMapper objectMapper;

    /*
     * # Naver Lucy Filter
     *
     */
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistration;
    }

    /*
     * # Custom XssProtectionFilter
     *
     */
    // @Bean
    // public FilterRegistrationBean<XssProtectionFilter> XssFilter(){
    //     FilterRegistrationBean<XssProtectionFilter> registrationBean = new FilterRegistrationBean<>();
    //
    //     registrationBean.setFilter(new XssProtectionFilter());
    //     registrationBean.setInitParameters(new HashMap<>() {{
    //         put("excludePatterns", "/h2/.*");
    //     }});
    //     registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    //
    //     return registrationBean;
    // }

    @Bean
    public MappingJackson2HttpMessageConverter characterEscapeConverter() {
        ObjectMapper objectMapper = this.objectMapper.copy();
        objectMapper.getFactory().setCharacterEscapes(new XssProtectionSupport());

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
