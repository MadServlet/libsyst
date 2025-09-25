package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserManager extends JpaRepository<User, BigInteger> {

}
