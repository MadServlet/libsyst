package com.proj.itstaym.manager.api;

import com.proj.itstaym.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface BookManager extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

}
