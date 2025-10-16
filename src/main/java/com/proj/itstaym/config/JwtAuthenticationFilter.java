package com.proj.itstaym.config;

import com.proj.itstaym.security.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("--- JwtAuthenticationFilter started for path: " + request.getServletPath() + " ---");
        System.out.println("Cookies sent with request: " + (request.getCookies() != null ? Arrays.toString(request.getCookies()) : "none"));

        String token = null;
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> "access-token".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        System.out.println("Token from access-token cookie: " + token);

        if (token != null) {
            try {
                System.out.println("Attempting to validate token: " + token);
                String username = jwtUtil.validateAndGetUsername(token);
                System.out.println("Username from token: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("Loading user details for: " + username);
                    var user = userDetailsService.loadUserByUsername(username);

                    if (user != null) {
                        System.out.println("User details loaded successfully.");
                        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println("SecurityContext populated with user: " + username);
                    } else {
                        System.out.println("User details not found for: " + username);
                    }
                } else {
                    System.out.println("SecurityContext already populated or username is null.");
                }
            } catch (JwtException e) {
                System.out.println("JWT validation failed: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("No access-token cookie found.");
        }

        System.out.println("--- JwtAuthenticationFilter finished ---");
        filterChain.doFilter(request, response);
    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        return pathMatcher.match("/api/auth", request.getServletPath()) ||
//                pathMatcher.match("/", request.getServletPath()) ||
//                pathMatcher.match("/login", request.getServletPath()) ||
//                pathMatcher.match("/css/**", request.getServletPath()) ||
//                pathMatcher.match("/js/**", request.getServletPath()) ||
//                pathMatcher.match("/img/**", request.getServletPath()) ||
//                pathMatcher.match("/web/**", request.getServletPath());
//    }
}
