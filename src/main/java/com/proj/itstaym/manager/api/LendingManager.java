package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingManager extends JpaRepository<Loan, Long> {



}
