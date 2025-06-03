package com.djcodes.spring.demoapp.controller;

import com.djcodes.spring.demoapp.openapi.api.BooksApi;
import com.djcodes.spring.demoapp.openapi.model.Book;
import com.djcodes.spring.demoapp.openapi.model.BookRequest;
import com.djcodes.spring.demoapp.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController implements BooksApi {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<Book>> booksGet(String title, String author) {
        List<Book> result = service.searchBooks(title, author);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @Override
    public ResponseEntity<Void> booksIdDelete(Long id) {
        boolean result = service.deleteBook(id);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Book> booksIdGet(Long id) {
        Optional<Book> result = service.getBookById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Book> booksIdPut(Long id, @Valid BookRequest bookRequest) {
        Optional<Book> updatedBook = service.updateBook(id, bookRequest);
        if (updatedBook.isPresent()) {
            return ResponseEntity.ok(updatedBook.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Book> booksPost(@Valid BookRequest bookRequest) {
        Book book = service.createBook(bookRequest);
        if (book != null) {
            return ResponseEntity.status(201).body(book);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
