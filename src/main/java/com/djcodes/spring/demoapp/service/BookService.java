package com.djcodes.spring.demoapp.service;


import com.djcodes.spring.demoapp.entity.BookEntity;
import com.djcodes.spring.demoapp.openapi.model.Book;
import com.djcodes.spring.demoapp.openapi.model.BookRequest;
import org.springframework.stereotype.Service;

import com.djcodes.spring.demoapp.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book createBook(BookRequest bookRequest) {
        // Generate a new Book object from the request
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublicationYear(bookRequest.getPublicationYear());
        // Convert to entity and save
        BookEntity entity = toEntity(book);
        BookEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public List<Book> getAllBooks() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<Book> getBookById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public List<Book> searchBooks(String title, String author) {
        return repository.searchBooks(title, author).stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<Book> updateBook(Long id, BookRequest bookRequest) {
        return repository.findById(id).map(entity -> {
            entity.setTitle(bookRequest.getTitle());
            entity.setAuthor(bookRequest.getAuthor());
            entity.setIsbn(bookRequest.getIsbn());
            entity.setPublicationYear(bookRequest.getPublicationYear());
            return toDto(repository.save(entity));
        });
    }

    public boolean deleteBook(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setIsbn(book.getIsbn());
        entity.setPublicationYear(book.getPublicationYear());
        return entity;
    }

    private Book toDto(BookEntity entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        book.setIsbn(entity.getIsbn());
        book.setPublicationYear(entity.getPublicationYear());
        return book;
    }
}
