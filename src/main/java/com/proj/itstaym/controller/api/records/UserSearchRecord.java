package com.proj.itstaym.controller.api.records;

import com.proj.itstaym.enums.Role;
import com.proj.itstaym.model.User;

public record UserSearchRecord(
        String email,
        String fullName,
        Role role
) {
    public User toEntity() {
        var user = new User();
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(role);
        return user;
    }
}
