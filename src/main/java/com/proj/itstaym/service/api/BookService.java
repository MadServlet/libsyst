package com.proj.itstaym.service.api;

import com.proj.itstaym.controller.records.BookRecord;

import java.math.BigInteger;

public interface BookService {

    BookRecord getBook(BigInteger id);

    BookRecord findBook(BookRecord bookRecord);

    BookRecord createBook(BookRecord bookRecord);

    BookRecord updateBook(BookRecord bookRecord);

    void deleteBook(Integer id);

}
