package com.proj.itstaym.service;

import com.proj.itstaym.controller.records.BookRecord;
import com.proj.itstaym.manager.api.BookManager;
import com.proj.itstaym.model.Book;
import com.proj.itstaym.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookManager bookManager;

    // Create
    @Override
    public BookRecord createBook(BookRecord bookRecord) {
        return BookRecord.from(bookManager.save(bookRecord.toEntity()));
    }

    @Override
    public List<BookRecord> createBooks(List<BookRecord> bookRecords) {
        return bookManager.saveAll(bookRecords.stream().map(BookRecord::toEntity).toList()).stream().map(BookRecord::from).toList();
    }

    // Read
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
        return bookManager.findAll(PageRequest.of(page, size)).stream().map(BookRecord::from).toList();
    }

    @Override
    public List<BookRecord> findByCriteria(BookRecord bookRecord) {

        var probe = bookRecord.toEntity();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Book> example = Example.of(probe, matcher);

        List<Book> books = bookManager.findAll(example);

        return books.stream()
                .map(BookRecord::from)
                .toList();
    }

    // Update
    @Override
    public BookRecord updateBook(BookRecord bookRecord) {
        return BookRecord.from(bookManager.save(bookRecord.toEntity()));
    }

    // Delete
    @Override
    public void deleteBook(BigInteger id) {
        bookManager.deleteById(id);
    }
}
