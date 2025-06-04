package com.djcodes.spring.demoapp.controller;

import com.djcodes.spring.demoapp.openapi.model.Book;
import com.djcodes.spring.demoapp.openapi.model.BookRequest;
import com.djcodes.spring.demoapp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
    }

    @Test
    void testBooksGet_Found() {
        List<Book> books = List.of(new Book().id(1L).title("Test Book").author("Author"));
        when(bookService.searchBooks("Test Book", "Author")).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.booksGet("Test Book", "Author");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(books, response.getBody());
    }

    @Test
    void testBooksGet_NotFound() {
        when(bookService.searchBooks("Nonexistent", "Unknown")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Book>> response = bookController.booksGet("Nonexistent", "Unknown");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testBooksIdDelete_Success() {
        when(bookService.deleteBook(1L)).thenReturn(true);

        ResponseEntity<Void> response = bookController.booksIdDelete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testBooksIdDelete_NotFound() {
        when(bookService.deleteBook(99L)).thenReturn(false);

        ResponseEntity<Void> response = bookController.booksIdDelete(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testBooksIdGet_Found() {
        Book book = new Book().id(1L).title("Test Book").author("Author");
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.booksIdGet(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void testBooksIdGet_NotFound() {
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.booksIdGet(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testBooksIdPut_Success() {
        BookRequest bookRequest = new BookRequest().title("Updated Title").author("Updated Author");
        Book updatedBook = new Book().id(1L).title("Updated Title").author("Updated Author");
        when(bookService.updateBook(1L, bookRequest)).thenReturn(Optional.of(updatedBook));

        ResponseEntity<Book> response = bookController.booksIdPut(1L, bookRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedBook, response.getBody());
    }

    @Test
    void testBooksIdPut_NotFound() {
        BookRequest bookRequest = new BookRequest().title("Updated Title").author("Updated Author");
        when(bookService.updateBook(99L, bookRequest)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.booksIdPut(99L, bookRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testBooksPost_Success() {
        BookRequest bookRequest = new BookRequest().title("New Book").author("New Author");
        Book createdBook = new Book().id(1L).title("New Book").author("New Author");
        when(bookService.createBook(bookRequest)).thenReturn(createdBook);

        ResponseEntity<Book> response = bookController.booksPost(bookRequest);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdBook, response.getBody());
    }

    @Test
    void testBooksPost_BadRequest() {
        BookRequest bookRequest = new BookRequest().title("").author("");
        when(bookService.createBook(bookRequest)).thenReturn(null);

        ResponseEntity<Book> response = bookController.booksPost(bookRequest);

        assertEquals(400, response.getStatusCodeValue());
    }
}