package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LendingManager extends JpaRepository<Loan, Long> {

    @Query("SELECT l from Loan l where l.user.email = :email ORDER BY l.loanDate DESC")
    Page<Loan> findByEmailPaged(@Param("email") String email, Pageable page);

    @Query("SELECT l from Loan l where l.user.email = :email ORDER BY l.loanDate DESC")
    List<Loan> findByEmail(@Param("email") String email);

}
