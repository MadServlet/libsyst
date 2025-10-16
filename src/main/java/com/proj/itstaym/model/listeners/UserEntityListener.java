package com.proj.itstaym.model.listeners;

import com.proj.itstaym.model.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserEntityListener {

    @PrePersist
    @PreUpdate
    public void encodePassword(User user) {
        String password = user.getPassword();
        if (password != null && !password.startsWith("$2a$")) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }
    }

}
