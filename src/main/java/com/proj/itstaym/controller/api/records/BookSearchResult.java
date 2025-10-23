package com.proj.itstaym.controller.api.records;

public record BookSearchResult(
        Long copyId,
        String title,
        String author,
        String publisher,
        Integer year,
        String status
) {}