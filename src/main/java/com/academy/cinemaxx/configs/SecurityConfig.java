package com.academy.cinemaxx.configs;

import com.academy.cinemaxx.security.filters.JwtTokenAuthenticationProcessingFilter;
import com.academy.cinemaxx.security.handlers.CustomAuthenticationFailureHandler;
import com.academy.cinemaxx.security.handlers.CustomAccessDeniedHandler;
import com.academy.cinemaxx.security.providers.JwtAuthenticationProvider;
import com.academy.cinemaxx.security.utils.JwtTokenFactory;
import com.academy.cinemaxx.security.utils.JwtTokenHeaderExtractor;
import com.academy.cinemaxx.security.utils.SkipPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtTokenHeaderExtractor tokenExtractor;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private static final String AUTH_URL = "/v1/auth/**";
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
        ObjectMapper objectMapper,
        JwtTokenFactory jwtTokenFactory,
        JwtTokenHeaderExtractor tokenExtractor,
        JwtAuthenticationProvider jwtAuthenticationProvider
    ) {
        this.objectMapper = objectMapper;
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
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(objectMapper);
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(objectMapper);
    }
    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return new ProviderManager(List.of(jwtAuthenticationProvider));
    }


    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler authenticationFailureHandler) {
        List<String> pathsToSkip = List.of(AUTH_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, V1_URL);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
                authenticationFailureHandler,
                tokenExtractor,
                matcher);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter,
            AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_URL).permitAll()
                        .requestMatchers(SWAGGER_URL).permitAll()
                        .requestMatchers(V1_URL).authenticated()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler))
                .securityMatcher(V1_URL)
                .anonymous(anonymous -> anonymous.disable());
            return http.build();
    }

}