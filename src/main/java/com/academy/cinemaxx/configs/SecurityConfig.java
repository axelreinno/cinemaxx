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
        EmailAuthenticationFilter emailAuthenticationFilter = new EmailAuthenticationFilter("/v1/auth/login", successHandler, failureHandler,
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
        OtpAuthenticationFilter otpAuthenticationFilter = new OtpAuthenticationFilter("/v1/auth/verify", successHandler, failureHandler,
                objectMapper);
        otpAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return otpAuthenticationFilter;
    }

    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler failureHandler) {
        List<String> pathsToSkip = Arrays.asList("/v1/auth/login", "/v1/auth/verify");
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, "/v1/**");
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
                        .requestMatchers("/v1/auth/login", "/v1/auth/verify").permitAll()
                        .requestMatchers("/v1/**").authenticated()
                        .anyRequest().authenticated())
                .securityMatcher("/v1/**")
                .anonymous(anonymous -> anonymous.disable());

        return http.build();
    }
} 