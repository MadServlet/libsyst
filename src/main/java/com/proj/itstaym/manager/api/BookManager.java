package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookManager {

    Book getBook(BigInteger id);

    List<Book> findBook(Book book);

    Book createBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Integer id);

}
