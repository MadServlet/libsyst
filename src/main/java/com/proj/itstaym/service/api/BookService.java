package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.controller.api.records.BookSearchResult;
import com.proj.itstaym.controller.api.records.BookStatisticsRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

public interface BookService {

    BookRecord find(Long id);

    List<BookRecord> findAll();

    List<BookRecord> findAll(Integer page, Integer size);

    List<BookRecord> findByCriteria(BookRecord bookRecord);

    Page<BookSearchResult> findRankedCopies(String search, Pageable pageable);

    BookStatisticsRecord countAll();

    BookRecord createBook(BookRecord bookRecord);

    List<BookRecord> createBooks(List<BookRecord> bookRecords);

    BookRecord updateBook(BookRecord bookRecord);

    void deleteBook(Long id);

}
