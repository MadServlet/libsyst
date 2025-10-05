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

    @GetMapping(params = "id")
    public BookRecord findById(@RequestParam("id") BigInteger id) {
        return bookService.find(id);
    }

    @GetMapping
    public List<BookRecord> getAll() {
        return bookService.findAll();
    }

    @PostMapping
    public BookRecord createBook(@RequestBody BookRecord bookRecord){
        return bookService.createBook(bookRecord);
    }

}
