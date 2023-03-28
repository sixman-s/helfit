package sixman.helfit.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sixman.helfit.domain.user.repository.UserRefreshTokenRepository;
import sixman.helfit.security.filter.JwtAuthenticationFilter;
import sixman.helfit.security.handler.*;
import sixman.helfit.security.properties.AppProperties;
import sixman.helfit.security.properties.CorsProperties;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.filter.JwtVerificationFilter;
import sixman.helfit.security.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import sixman.helfit.security.service.CustomOAuth2UserService;
import sixman.helfit.security.service.CustomUserDetailService;
import sixman.helfit.security.token.AuthTokenProvider;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${domain.front}")
    private String frontDomain;

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;

    private final AuthTokenProvider authTokenProvider;
    private final CustomUserDetailService customUserDetailService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers()
                .xssProtection()
            .and()
                .contentSecurityPolicy("script-src 'self'")
            .and()
                .frameOptions()
                .sameOrigin()
            .and()
                .cors()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout()
                    .logoutSuccessUrl(frontDomain)
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID", "refresh_token")
            .and()
                .apply(new CustomFilterConfigurer())
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/",
                    "/error",
                    "/favicon.ico",
                    "/**/*.png",
                    "/**/*.gif",
                    "/**/*.svg",
                    "/**/*.jpg",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js"
                ).permitAll()
                // # Ex: RoleType.USER 인가 설정
                // .antMatchers("/api/**/users/**").hasAnyAuthority(RoleType.USER.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
            .and()
                .redirectionEndpoint()
                    .baseUri("/*/oauth2/code/*")
            .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                            .antMatchers("/h2/**");
    }

    /*
     * # AuthenticationManager
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * # PasswordEncoder
     *
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * # 토큰 검증 필터
     *
     */
    @Bean
    public JwtVerificationFilter jwtVerificationFilter() {
        return new JwtVerificationFilter(authTokenProvider, customUserDetailService);
    }

    /*
     * # 토큰 필터
     *  ! 필터 핸들러 필요시 로그인 프로세스 절차 변경
     *
     */
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(appProperties, authenticationManager, authTokenProvider, userRefreshTokenRepository);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/users/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

        return jwtAuthenticationFilter;
    }

    /*
     * # 쿠키 기반 인가 Repository
     *
     */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * # Oauth 인증 성공 핸들러
     *
     */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
            appProperties,
            authTokenProvider,
            userRefreshTokenRepository,
            oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * # Oauth 인증 실패 핸들러
     *
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
     * # Cors
     *
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOriginPatterns(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);

        return corsConfigSource;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // ! 필터 핸들러 필요시 로그인 프로세스 절차 변경
            // AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            // builder.addFilter(jwtAuthenticationFilter(authenticationManager));
            builder.addFilterBefore(jwtVerificationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
