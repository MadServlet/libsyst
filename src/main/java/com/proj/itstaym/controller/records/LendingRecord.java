package com.proj.itstaym.controller.records;

import java.time.LocalDateTime;

public record LendingRecord(BookRecord book, UserRecord borrower, UserRecord issuer,
                            LocalDateTime release, LocalDateTime returnDeadline,
                            LocalDateTime returnedDateTime) {
}
