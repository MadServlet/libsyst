package com.proj.itstaym.controller.api.records;

import com.proj.itstaym.model.Book;

import java.time.Year;

public record BookRecord(Long id, String title, String author,
                         String edition, String publisher, Year year) {

    public static BookRecord from(Book book) {
        return new BookRecord(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getEdition(),
                book.getPublisher(),
                book.getYear()
        );
    }

    public Book toEntity() {
        var book = new Book();
        book.setId(this.id());
        book.setTitle(this.title());
        book.setAuthor(this.author());
        book.setEdition(this.edition());
        book.setPublisher(this.publisher());
        book.setYear(this.year());
        return book;
    }
}
