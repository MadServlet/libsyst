package com.proj.itstaym.libsyst.controller.records;

import com.proj.itstaym.libsyst.enums.Role;

import java.math.BigInteger;

public record UserRecord(BigInteger id, String email, String fullName, Role role, String password) { }
