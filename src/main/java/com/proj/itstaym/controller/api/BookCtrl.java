package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookCtrl {

    private final BookService bookService;

    @Autowired
    public BookCtrl(BookService bookService) {
        this.bookService = bookService;
    }

    // Create
    @PostMapping
    public BookRecord saveBook(@RequestBody BookRecord bookRecord){
        return bookService.createBook(bookRecord);
    }

    @PostMapping
    public List<BookRecord> saveBook(@RequestBody List<BookRecord> bookRecords){
        return bookService.createBooks(bookRecords);
    }

    // Read
    @GetMapping(params = "id")
    public BookRecord findById(@RequestParam("id") BigInteger id) {
        return bookService.find(id);
    }

    @GetMapping
    public List<BookRecord> findAll() {
        return bookService.findAll();
    }

    @GetMapping(params = {"page","size"})
    public List<BookRecord> findAll(Integer page, Integer size) {
        return bookService.findAll(page, size);
    }

    @PostMapping
    public List<BookRecord> findByCriteria(@RequestBody BookRecord bookRecord) {
        return bookService.findByCriteria(bookRecord);
    }

    // Update
    @PostMapping
    public BookRecord updateBook(@RequestBody BookRecord bookRecord) {
        return bookService.updateBook(bookRecord);
    }

    // Delete
    @DeleteMapping(params = "id")
    public void deleteBook(@RequestParam("id") BigInteger id) {
        bookService.deleteBook(id);
    }

}
