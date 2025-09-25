package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.User;

import java.math.BigInteger;

public interface UserManagerCustom {
    User getUser(BigInteger id);

    User getUser(String email);

    User getUsers(Integer page, Integer count);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Integer id);

    void deleteUser(String email);
}
