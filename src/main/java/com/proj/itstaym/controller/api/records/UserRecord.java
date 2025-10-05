package com.proj.itstaym.controller.api.records;

import com.proj.itstaym.enums.Role;
import com.proj.itstaym.model.User;

import java.math.BigInteger;


public record UserRecord(
        BigInteger id,
        String email,
        String fullName,
        Role role,
        String password
) {

    public static UserRecord from(User user) {
        return new UserRecord(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getPassword()
        );
    }

    public User to() {
        var user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(role);
        user.setPassword(password);
        return user;
    }
}
