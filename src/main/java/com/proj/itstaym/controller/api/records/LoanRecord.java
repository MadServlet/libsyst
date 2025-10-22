package com.proj.itstaym.controller.api.records;

import com.proj.itstaym.enums.LoanStatus;

import java.time.LocalDateTime;

public record LoanRecord (
        Long id,
        UserRecord user,
        BookRecord book,
        LocalDateTime loanDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        LoanStatus status) {
}
