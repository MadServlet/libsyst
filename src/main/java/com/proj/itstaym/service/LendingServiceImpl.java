package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.controller.api.records.LoanRecord;
import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.manager.api.LendingManager;
import com.proj.itstaym.service.api.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private LendingManager lendingManager;

    @Override
    public Page<LoanRecord> findAll(Pageable page) {

        var result = lendingManager.findAll(page);

        return result.map(loan -> new LoanRecord(
                loan.getId(),
                new UserRecord(
                        loan.getUser().getId(),
                        loan.getUser().getEmail(),
                        loan.getUser().getFullName(),
                        loan.getUser().getRole(),
                        null
                ),
                new BookRecord(
                        loan.getBookCopy().getBook().getId(),
                        loan.getBookCopy().getBook().getTitle(),
                        loan.getBookCopy().getBook().getAuthor(),
                        loan.getBookCopy().getBook().getEdition(),
                        loan.getBookCopy().getBook().getPublisher(),
                        loan.getBookCopy().getBook().getYear()
                ),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        ));
    }

    @Override
    public List<LoanRecord> findAll() {
        var result = lendingManager.findAll();

        return result.stream()
                .map(loan -> new LoanRecord(
                        loan.getId(),
                        new UserRecord(
                                loan.getUser().getId(),
                                loan.getUser().getEmail(),
                                loan.getUser().getFullName(),
                                loan.getUser().getRole(),
                                null
                        ),
                        new BookRecord(
                                loan.getBookCopy().getBook().getId(),
                                loan.getBookCopy().getBook().getTitle(),
                                loan.getBookCopy().getBook().getAuthor(),
                                loan.getBookCopy().getBook().getEdition(),
                                loan.getBookCopy().getBook().getPublisher(),
                                loan.getBookCopy().getBook().getYear()
                        ),
                        loan.getLoanDate(),
                        loan.getDueDate(),
                        loan.getReturnDate(),
                        loan.getStatus()
                ))
                .collect(Collectors.toList());
    }

}
