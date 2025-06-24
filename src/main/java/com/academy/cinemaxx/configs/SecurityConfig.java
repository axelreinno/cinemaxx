package com.academy.cinemaxx.configs;

import com.academy.cinemaxx.security.filters.EmailAuthenticationFilter;
import com.academy.cinemaxx.security.filters.JwtTokenAuthenticationProcessingFilter;
import com.academy.cinemaxx.security.filters.OtpAuthenticationFilter;
import com.academy.cinemaxx.security.handlers.AuthFailureHandler;
import com.academy.cinemaxx.security.handlers.EmailAuthenticationSuccessHandler;
import com.academy.cinemaxx.security.handlers.OtpAuthenticationSuccessHandler;
import com.academy.cinemaxx.security.providers.EmailAuthenticationProvider;
import com.academy.cinemaxx.security.providers.JwtAuthenticationProvider;
import com.academy.cinemaxx.security.providers.OtpAuthenticationProvider;
import com.academy.cinemaxx.security.utils.JwtTokenFactory;
import com.academy.cinemaxx.security.utils.JwtTokenHeaderExtractor;
import com.academy.cinemaxx.security.utils.SkipPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtTokenFactory jwtTokenFactory;
    private final JwtTokenHeaderExtractor tokenExtractor;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final EmailAuthenticationProvider emailAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;

    private static final String LOGIN_URL = "/v1/auth/login";
    private static final String VERIFY_URL = "/v1/auth/verify";
    private static final String V1_URL = "/v1/**";
    private static final String[] SWAGGER_URL = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-resources/**",
            "/webjars/**"
    };

    public SecurityConfig(
            EmailAuthenticationProvider emailAuthenticationProvider,
            OtpAuthenticationProvider otpAuthenticationProvider,
            ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory,
            JwtTokenHeaderExtractor tokenExtractor,
            JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.emailAuthenticationProvider = emailAuthenticationProvider;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.objectMapper = objectMapper;
        this.jwtTokenFactory = jwtTokenFactory;
        this.tokenExtractor = tokenExtractor;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler otpSuccessHandler() {
        return new OtpAuthenticationSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationSuccessHandler emailAuthenticationSuccessHandler() {
        return new EmailAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new AuthFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return new ProviderManager(Arrays.asList(
            emailAuthenticationProvider,
            otpAuthenticationProvider,
            jwtAuthenticationProvider
        ));
    }


    @Bean
    public EmailAuthenticationFilter emailAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("emailAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        EmailAuthenticationFilter emailAuthenticationFilter = new EmailAuthenticationFilter(LOGIN_URL, successHandler, failureHandler,
                objectMapper);
        emailAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return emailAuthenticationFilter;
    }

    @Bean
    public OtpAuthenticationFilter otpAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("otpSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        OtpAuthenticationFilter otpAuthenticationFilter = new OtpAuthenticationFilter(VERIFY_URL, successHandler, failureHandler,
                objectMapper);
        otpAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return otpAuthenticationFilter;
    }

    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler failureHandler) {
        List<String> pathsToSkip = Arrays.asList(LOGIN_URL, VERIFY_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, V1_URL);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
                failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            EmailAuthenticationFilter emailAuthenticationFilter,
            OtpAuthenticationFilter otpAuthenticationFilter,
            JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(otpAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(emailAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(VERIFY_URL, VERIFY_URL).permitAll()
                        .requestMatchers(SWAGGER_URL).permitAll()
                        .requestMatchers(V1_URL).authenticated()
                        .anyRequest().authenticated())
                .securityMatcher(V1_URL)
                .anonymous(anonymous -> anonymous.disable());

        return http.build();
    }
}