package sixman.helfit.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sixman.helfit.security.utils.XssUtils;
import sixman.helfit.security.wrapper.XssRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XssProtectionFilter implements Filter {

    // private String excludePatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        // this.excludePatterns = filterConfig.getInitParameter("excludePatterns");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XssRequestWrapper wrappedRequest = new XssRequestWrapper((HttpServletRequest) request);

        if (
            (
                wrappedRequest.getMethod().equalsIgnoreCase("post") ||
                wrappedRequest.getMethod().equalsIgnoreCase("patch")
            )
            &&
            (
                // Post & Patch Body Nullpointexception 예외처리
                request.getContentType() != null &&
                    (
                        request.getContentType().equals("application/json")
                        // request.getContentType().startsWith("multipart/form-data;")
                    )
            )
        ) {
            String body = IOUtils.toString(wrappedRequest.getInputStream());

            if (!StringUtils.isBlank(body)) {
                Map<String, Object> oldJsonObject = new ObjectMapper().readValue(body, HashMap.class);
                Map<String, Object> newJsonObject = new HashMap<>();

                oldJsonObject.forEach(
                    (key, value) -> newJsonObject.put(key, XssUtils.charEscape(value.toString()))
                );

                wrappedRequest.resetInputStream(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(newJsonObject).getBytes()
                );
            }
        }

        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
