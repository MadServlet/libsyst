package com.proj.itstaym.controller.api.records;

import com.proj.itstaym.enums.Role;
import com.proj.itstaym.model.User;


public record UserRecord(
        Long id,
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

    public User toEntity() {
        var user = new User();
        if (id != null) user.setId(id);
        if (email != null && !email.isEmpty()) user.setEmail(email);
        if (fullName != null && !fullName.isEmpty()) user.setFullName(fullName);
        if (role != null) user.setRole(role);
        if (password != null && !password.isEmpty()) user.setPassword(password);
        return user;
    }
}
