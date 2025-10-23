package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.controller.api.records.LoanRecord;
import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.manager.api.LendingManager;
import com.proj.itstaym.model.Loan;
import com.proj.itstaym.service.api.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendingServiceImpl implements LendingService {

    @Autowired
    private LendingManager lendingManager;

    @Override
    public Page<LoanRecord> findAll(Pageable page) {
        Pageable sortedPage = PageRequest.of(
                page.getPageNumber(),
                page.getPageSize(),
                Sort.by(Sort.Direction.DESC, "loanDate")
        );
        return resultToPageRecord(lendingManager.findAll(page));
    }

    @Override
    public List<LoanRecord> findAll() {
        return resultToListRecord(lendingManager.findAll(Sort.by(Sort.Direction.DESC, "loanDate")));
    }

    @Override
    public Page<LoanRecord> findByEmailPaged(String email, Pageable page) {
        return resultToPageRecord(lendingManager.findByEmailPaged(email, page));
    }

    @Override
    public List<LoanRecord> findByEmail(String email) {
        return resultToListRecord(lendingManager.findByEmail(email));
    }

    private Page<LoanRecord> resultToPageRecord(Page<Loan> record) {
        return record.map(loan -> new LoanRecord(
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

    private List<LoanRecord> resultToListRecord(List<Loan> record) {
        return record.stream()
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
