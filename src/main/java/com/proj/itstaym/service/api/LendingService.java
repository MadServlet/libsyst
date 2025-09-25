package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.records.BookRecord;
import com.proj.itstaym.controller.records.LendingRecord;

import java.math.BigInteger;

public interface LendingService {

    LendingRecord getLendingRecord(BigInteger id);

    LendingRecord findLendingRecord(BookRecord bookRecord);

    LendingRecord createLendingRecord(BookRecord bookRecord);

    LendingRecord updateLendingRecord(BookRecord bookRecord);

    void deleteLendingRecord(Integer id);

}
