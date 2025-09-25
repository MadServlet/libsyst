package com.proj.itstaym.libsyst.model;

import java.time.LocalDateTime;

public class Lending {

    private Book book;
    private User borrower;
    private User issuer;
    private LocalDateTime release;
    private LocalDateTime returnDeadline;
    private LocalDateTime returnedDateTime;

    public Lending() {

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public LocalDateTime getRelease() {
        return release;
    }

    public void setRelease(LocalDateTime release) {
        this.release = release;
    }

    public LocalDateTime getReturnDeadline() {
        return returnDeadline;
    }

    public void setReturnDeadline(LocalDateTime returnDeadline) {
        this.returnDeadline = returnDeadline;
    }

    public LocalDateTime getReturnedDateTime() {
        return returnedDateTime;
    }

    public void setReturnedDateTime(LocalDateTime returnedDateTime) {
        this.returnedDateTime = returnedDateTime;
    }
}
