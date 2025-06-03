package com.djcodes.spring.demoapp.repository;

import com.djcodes.spring.demoapp.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT b FROM BookEntity b WHERE (:title IS NULL OR b.title LIKE %:title%) AND (:author IS NULL OR b.author LIKE %:author%)")
    List<BookEntity> searchBooks(String title, String author);
}
