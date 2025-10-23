package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.api.records.LoanRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LendingService {

    // create

    // read
    Page<LoanRecord> findAll(Pageable page);
    List<LoanRecord> findAll();
    Page<LoanRecord> findByEmailPaged(String email, Pageable page);
    List<LoanRecord> findByEmail(String email);
    // update

    // delete

}
