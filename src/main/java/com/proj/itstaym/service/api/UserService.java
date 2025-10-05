package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.records.UserRecord;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserRecord createUser(UserRecord user);

    List<UserRecord> createUsers(List<UserRecord> userRecords);

    Optional<UserRecord> find(BigInteger id);

    Optional<UserRecord> find(String email);

    List<UserRecord> findAll(Integer page, Integer size);

    List<UserRecord> findByCriteria(UserRecord userRecord, Integer page, Integer size);

    UserRecord updateUser(UserRecord user);

    List<UserRecord> updateUsers(List<UserRecord> userRecords);

    void deleteUser(BigInteger id);

    void deleteUser(String email);

}
