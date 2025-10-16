package com.proj.itstaym.libsyst;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "pass";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);

        // verify
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches? " + matches);
    }
}
