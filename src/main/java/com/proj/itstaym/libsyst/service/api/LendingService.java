package com.proj.itstaym.libsyst.service.api;

import com.proj.itstaym.libsyst.controller.records.BookRecord;
import com.proj.itstaym.libsyst.controller.records.LendingRecord;

import java.math.BigInteger;

public interface LendingService {

    LendingRecord getLendingRecord(BigInteger id);

    LendingRecord findLendingRecord(BookRecord bookRecord);

    LendingRecord createLendingRecord(BookRecord bookRecord);

    LendingRecord updateLendingRecord(BookRecord bookRecord);

    void deleteLendingRecord(Integer id);

}
