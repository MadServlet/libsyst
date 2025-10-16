package com.proj.itstaym.exception.user;

import java.math.BigInteger;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(BigInteger id) {
        super("User with id " + id + " not found");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }

}
