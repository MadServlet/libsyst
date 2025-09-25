package com.proj.itstaym.service;

import com.proj.itstaym.controller.records.UserRecord;
import com.proj.itstaym.manager.api.UserManager;
import com.proj.itstaym.model.User;
import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserRecord getUser(BigInteger id) {
        return userManager.findById(id).map(user -> new UserRecord(user.getId(), user.getEmail(), user.getFullName(),
                user.getRole(), user.getPassword())).orElse(null);
    }

    @Override
    public UserRecord getUser(String email) {
        return null;
    }

    @Override
    public UserRecord getUsers(Integer page, Integer count) {
        return null;
    }

    @Override
    public UserRecord createUser(UserRecord user) {

        var userModel = new User();
        userModel.setEmail(user.email());
        userModel.setPassword(DigestUtils.md5DigestAsHex(user.password().getBytes()));
        userModel.setRole(user.role());
        userModel.setFullName(user.fullName());

        var data = userManager.save(userModel);
        return new UserRecord(data.getId(), data.getEmail(), data.getFullName(),
                data.getRole(), data.getPassword());
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
