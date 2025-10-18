package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.controller.api.records.UserSearchRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserRecord createUser(UserRecord user);

    List<UserRecord> createUsers(List<UserRecord> userRecords);

    Optional<UserRecord> find(Long id);

    Optional<UserRecord> find(String email);

    List<UserRecord> findAll(Integer page, Integer size);

    Page<UserRecord> findByCriteria(UserSearchRecord userSearchRecord, Pageable pageable);

    UserRecord updateUser(UserRecord user);

    List<UserRecord> updateUsers(List<UserRecord> userRecords);

    void deleteUser(Long id);

    void deleteUser(String email);

}
