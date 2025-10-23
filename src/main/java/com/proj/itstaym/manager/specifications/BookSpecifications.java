package com.proj.itstaym.manager.specifications;

import com.proj.itstaym.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return null; // no filter
        }

        String like = "%" + keyword.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("title")), like),
                cb.like(cb.lower(root.get("author")), like),
                cb.like(cb.lower(root.get("publisher")), like),
                cb.like(cb.toString(root.get("year")), like)
        );
    }
}
