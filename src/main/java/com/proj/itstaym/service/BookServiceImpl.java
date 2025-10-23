package com.proj.itstaym.service;

import com.proj.itstaym.controller.api.records.BookRecord;
import com.proj.itstaym.controller.api.records.BookSearchResult;
import com.proj.itstaym.controller.api.records.BookStatisticsRecord;
import com.proj.itstaym.enums.CopyStatus;
import com.proj.itstaym.manager.api.BookManager;
import com.proj.itstaym.manager.specifications.BookSpecifications;
import com.proj.itstaym.model.Book;
import com.proj.itstaym.model.BookCopy;
import com.proj.itstaym.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public BookRecord find(Long id) {
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

    @Override
    public Page<BookSearchResult> findRankedCopies(String search, Pageable pageable) {
        Specification<Book> spec = BookSpecifications.search(search);
        Page<Book> books = (spec != null)
                ? bookManager.findAll(spec, pageable)
                : bookManager.findAll(pageable);

        return books.map(book -> {
            BookCopy bestCopy = book.getCopies().stream()
                    .min(Comparator.comparingInt(copy -> statusPriority(copy.getStatus())))
                    .orElse(null);

            return new BookSearchResult(
                    bestCopy != null ? bestCopy.getId() : null,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getYear() != null ? book.getYear().getValue() : null,
                    bestCopy != null ? bestCopy.getStatus().name() : "NONE"
            );
        });
    }

    private int statusPriority(CopyStatus status) {
        return switch (status) {
            case AVAILABLE -> 1;
            case RESERVED -> 2;
            case LOANED -> 3;
            case DAMAGED -> 4;
            case LOST -> 5;
        };
    }

    @Override
    public BookStatisticsRecord countAll() {
        var totalCount = bookManager.count();
        return new BookStatisticsRecord(totalCount, 0L, 0L);
    }

    // Update
    @Override
    public BookRecord updateBook(BookRecord bookRecord) {
        return BookRecord.from(bookManager.save(bookRecord.toEntity()));
    }

    // Delete
    @Override
    public void deleteBook(Long id) {
        bookManager.deleteById(id);
    }
}
