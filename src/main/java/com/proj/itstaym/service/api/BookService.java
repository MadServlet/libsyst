package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.api.records.BookRecord;

import java.math.BigInteger;
import java.util.List;

public interface BookService {

    BookRecord find(BigInteger id);

    List<BookRecord> findAll();

    List<BookRecord> findAll(Integer page, Integer size);

    BookRecord findBook(BookRecord bookRecord);

    BookRecord createBook(BookRecord bookRecord);

    BookRecord updateBook(BookRecord bookRecord);

    void deleteBook(Integer id);

}
