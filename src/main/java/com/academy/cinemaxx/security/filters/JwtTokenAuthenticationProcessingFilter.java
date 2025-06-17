package com.academy.cinemaxx.security.filters;

import com.academy.cinemaxx.security.authentications.JwtAuthenticationToken;
import com.academy.cinemaxx.security.authentications.RawAccessJwtToken;
import com.academy.cinemaxx.security.utils.JwtTokenHeaderExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final JwtTokenHeaderExtractor tokenExtractor;

    public JwtTokenAuthenticationProcessingFilter(
            AuthenticationFailureHandler failureHandler,
            JwtTokenHeaderExtractor tokenExtractor,
            RequestMatcher matcher
    ) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader("Authorization");
        RawAccessJwtToken token = (RawAccessJwtToken) tokenExtractor.extract(tokenPayload);
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain, Authentication authResult
    ) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}