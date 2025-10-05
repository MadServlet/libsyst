package com.proj.itstaym.manager;

import com.proj.itstaym.manager.api.UserManagerCustom;
import com.proj.itstaym.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigInteger;

public class UserManagerImpl implements UserManagerCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser(BigInteger id) {
        return null;
    }

    @Override
    public User getUser(String email) {
        return null;
    }

    @Override
    public User getUsers(Integer page, Integer count) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public void deleteUser(String email) {

    }
}
