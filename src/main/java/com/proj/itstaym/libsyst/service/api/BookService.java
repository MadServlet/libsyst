package com.proj.itstaym.libsyst.service.api;

import com.proj.itstaym.libsyst.controller.records.BookRecord;

import java.math.BigInteger;

public interface BookService {

    BookRecord getBook(BigInteger id);

    BookRecord findBook(BookRecord bookRecord);

    BookRecord createBook(BookRecord bookRecord);

    BookRecord updateBook(BookRecord bookRecord);

    void deleteBook(Integer id);

}
