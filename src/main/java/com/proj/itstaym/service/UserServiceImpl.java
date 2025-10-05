package com.proj.itstaym.service;

import com.proj.itstaym.controller.records.UserRecord;
import com.proj.itstaym.manager.api.UserManager;
import com.proj.itstaym.model.User;
import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserRecord createUser(UserRecord user) {
        return UserRecord.from(userManager.save(user.toEntity()));
    }

    @Override
    public List<UserRecord> createUsers(List<UserRecord> userRecords) {
        return userManager.saveAll(
                userRecords.stream().map(UserRecord::toEntity).toList()
        ).stream().map(UserRecord::from).toList();
    }

    @Override
    public Optional<UserRecord> find(BigInteger id) {
        return userManager.findById(id).map(UserRecord::from);
    }

    @Override
    public Optional<UserRecord> find(String email) {
        return userManager.findByEmail(email).map(UserRecord::from);
    }

    @Override
    public List<UserRecord> findAll(Integer page, Integer size) {
        return userManager.findAll(PageRequest.of(page, size)).stream().map(UserRecord::from).toList();
    }

    @Override
    public List<UserRecord> findByCriteria(UserRecord userRecord, Integer page, Integer size) {
        var probe = userRecord.toEntity();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<User> example = Example.of(probe, matcher);

        List<User> users = userManager.findAll(example);

        return users.stream()
                .map(UserRecord::from)
                .toList();
    }

    @Override
    public UserRecord updateUser(UserRecord user) {
        return UserRecord.from(userManager.save(user.toEntity()));
    }

    @Override
    public List<UserRecord> updateUsers(List<UserRecord> userRecords) {
        return List.of();
    }

    @Override
    public void deleteUser(BigInteger id) {
        userManager.deleteById(id);
    }

    @Override
    public void deleteUser(String email) {
        userManager.deleteByEmail(email);
    }
}
