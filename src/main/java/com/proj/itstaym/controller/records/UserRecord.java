package com.proj.itstaym.controller.records;

import com.proj.itstaym.enums.Role;

import java.math.BigInteger;

public record UserRecord(BigInteger id, String email, String fullName,
                         Role role, String password) {
}
