package com.kacielriff.task_management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private static final String TOKEN_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(
                HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromHeader(request);

        try {
            UsernamePasswordAuthenticationToken userId = tokenService.isValid(token);

            SecurityContextHolder.getContext().setAuthentication(userId);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token inválido");
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        return token == null
                ? null
                : token.replace(TOKEN_PREFIX, "");
    }
}
