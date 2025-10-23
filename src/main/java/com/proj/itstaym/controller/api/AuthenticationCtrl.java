package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.AuthRecord;
import com.proj.itstaym.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthenticationCtrl {

    private final AuthenticationProvider authenticationProvider;
    private final JwtUtil jwtUtil;

    public AuthenticationCtrl(AuthenticationProvider authenticationProvider, JwtUtil jwtUtil) {
        this.authenticationProvider = authenticationProvider;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRecord req, HttpServletResponse response) {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
            String token = jwtUtil.generateToken(req.username());

            ResponseCookie cookie = ResponseCookie.from("access-token", token)
                    .httpOnly(true)
                    .secure(false) // Set to true in production over HTTPS
                    .path("/")
                    .maxAge(3600) // 1 hour
                    .sameSite("Lax") // Set to 'Strict' or 'Lax' for CSRF protection
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(Map.of("message", "Login successful"));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

}
