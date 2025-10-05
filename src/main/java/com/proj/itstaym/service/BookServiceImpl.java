package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.manager.api.BookManager;
import com.proj.itstaym.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookManager bookManager;

    @Override
    public BookRecord find(BigInteger id) {
        return bookManager.findById(id)
                .map(BookRecord::from)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
    }

    @Override
    public List<BookRecord> findAll() {
        return bookManager.findAll()
                .stream()
                .map(BookRecord::from)
                .toList();
    }

    @Override
    public List<BookRecord> findAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public BookRecord findBook(BookRecord bookRecord) {
        return null;
    }

    @Override
    public BookRecord createBook(BookRecord bookRecord) {
        var result = bookManager.save(bookRecord.toEntity());
        return BookRecord.from(result);
    }

    @Override
    public BookRecord updateBook(BookRecord bookRecord) {
        return null;
    }

    @Override
    public void deleteBook(Integer id) {

    }
}
