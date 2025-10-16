package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.api.records.BookRecord;

import java.math.BigInteger;
import java.util.List;

public interface BookService {

    BookRecord find(BigInteger id);

    List<BookRecord> findAll();

    List<BookRecord> findAll(Integer page, Integer size);

    List<BookRecord> findByCriteria(BookRecord bookRecord);

    BookRecord createBook(BookRecord bookRecord);

    List<BookRecord> createBooks(List<BookRecord> bookRecords);

    BookRecord updateBook(BookRecord bookRecord);

    void deleteBook(BigInteger id);

}
