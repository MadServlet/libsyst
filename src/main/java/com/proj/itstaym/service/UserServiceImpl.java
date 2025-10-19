package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.controller.api.records.UserSearchRecord;
import com.proj.itstaym.manager.api.UserManager;
import com.proj.itstaym.model.User;
import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserRecord createUser(UserRecord user) {
        var entity = user.toEntity();

        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }

        return UserRecord.from(userManager.save(entity));
    }

    @Override
    public List<UserRecord> createUsers(List<UserRecord> userRecords) {

        var entities = userRecords.stream()
                .map(UserRecord::toEntity)
                .peek(entity -> {
                    if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
                        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                    }
                })
                .toList();

        return userManager.saveAll(entities)
                .stream()
                .map(UserRecord::from)
                .toList();
    }

    @Override
    public Optional<UserRecord> find(Long id) {
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
    public Page<UserRecord> findByCriteria(UserSearchRecord userSearchRecord, Pageable pageable) {
        var probe = userSearchRecord.toEntity();

        var matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        var example = Example.of(probe, matcher);

        var userPage = userManager.findAll(example, pageable);

        var userRecords = userPage.getContent().stream()
                .map(UserRecord::from)
                .collect(Collectors.toList());

        return new PageImpl<>(userRecords, pageable, userPage.getTotalElements());
    }

    @Override
    public UserRecord updateUser(UserRecord updatedUserRecord) {
        Optional<User> optionalUser = userManager.findById(updatedUserRecord.id());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (updatedUserRecord.fullName() != null && !updatedUserRecord.fullName().isEmpty()) {
                existingUser.setFullName(updatedUserRecord.fullName());
            }
            if (updatedUserRecord.email() != null && !updatedUserRecord.email().isEmpty()) {
                existingUser.setEmail(updatedUserRecord.email());
            }
            if (updatedUserRecord.role() != null) {
                existingUser.setRole(updatedUserRecord.role());
            }
            if (updatedUserRecord.password() != null && !updatedUserRecord.password().isEmpty()) {
                existingUser.setPassword(updatedUserRecord.password());
            }

            User savedUser = userManager.save(existingUser);
            return UserRecord.from(savedUser);
        } else {
            throw new RuntimeException("User with ID " + updatedUserRecord.id() + " not found.");
        }
    }

    @Override
    public List<UserRecord> updateUsers(List<UserRecord> userRecords) {
        return List.of();
    }

    @Override
    public void deleteUser(Long id) {
        userManager.deleteById(id);
    }

    @Override
    public void deleteUser(String email) {
        userManager.deleteByEmail(email);
    }
}
