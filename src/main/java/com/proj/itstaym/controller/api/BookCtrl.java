package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.controller.api.records.BookSearchResult;
import com.proj.itstaym.controller.api.records.BookStatisticsRecord;
import com.proj.itstaym.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/save")
    public BookRecord saveBook(@RequestBody BookRecord bookRecord) {
        return bookService.createBook(bookRecord);
    }

    @PostMapping("/save/bulk")
    public List<BookRecord> saveBook(@RequestBody List<BookRecord> bookRecords) {
        return bookService.createBooks(bookRecords);
    }

    // Read
    @GetMapping(path = "/find", params = "id")
    public BookRecord findById(@RequestParam("id") Long id) {
        return bookService.find(id);
    }

    @GetMapping(path = "/find")
    public List<BookRecord> findAll() {
        return bookService.findAll();
    }

    @GetMapping(path = "/find", params = {"page", "size"})
    public List<BookRecord> findAll(Integer page, Integer size) {
        return bookService.findAll(page, size);
    }

    @PostMapping(path = "/find")
    public List<BookRecord> findByCriteria(@RequestBody BookRecord bookRecord) {
        return bookService.findByCriteria(bookRecord);
    }

    @GetMapping(path = "/find/bulk/available")
    public Page<BookSearchResult> findBestCopies(
            @RequestParam(required = false, name = "search", defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size) {

        var pageable = PageRequest.of(page, size);
        return bookService.findRankedCopies(search, pageable);
    }

    @GetMapping(path = "/count")
    public BookStatisticsRecord countAll() {
        return bookService.countAll();
    }

    // Update
    @PatchMapping("/update")
    public BookRecord updateBook(@RequestBody BookRecord bookRecord) {
        return bookService.updateBook(bookRecord);
    }

    // Delete
    @DeleteMapping(path = "/update", params = "id")
    public void deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
    }

}
