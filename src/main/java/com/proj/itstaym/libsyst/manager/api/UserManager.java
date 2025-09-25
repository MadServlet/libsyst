package com.proj.itstaym.libsyst.manager.api;

import com.proj.itstaym.libsyst.model.User;

import java.math.BigInteger;

public interface UserManager {

    User getUser(BigInteger id);

    User getUser(String email);

    User getUsers(Integer page, Integer count);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Integer id);

    void deleteUser(String email);

}
