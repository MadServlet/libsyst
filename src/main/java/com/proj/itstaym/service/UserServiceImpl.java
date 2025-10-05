package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.manager.api.UserManager;
import com.proj.itstaym.model.User;
import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserRecord getUser(BigInteger id) {
        return userManager.findById(id).map(UserRecord::from).orElse(null);
    }

    @Override
    public Optional<UserRecord> getUser(String email) {
        return userManager.findByEmail(email).map(UserRecord::from);
    }

    @Override
    public UserRecord getUsers(Integer page, Integer count) {
        return null;
    }

    @Override
    public UserRecord createUser(UserRecord user) {
        return UserRecord.from(userManager.save(user.to()));
    }

    @Override
    public UserRecord updateUser(UserRecord user) {
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public void deleteUser(String email) {

    }
}
