package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.records.UserRecord;

import java.math.BigInteger;
import java.util.Optional;

public interface UserService {

    UserRecord getUser(BigInteger id);

    Optional<UserRecord> getUser(String email);

    UserRecord getUsers(Integer page, Integer count);

    UserRecord createUser(UserRecord user);

    UserRecord updateUser(UserRecord user);

    void deleteUser(Integer id);

    void deleteUser(String email);

}
