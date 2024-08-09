package com.example.jpa3.controller;

import com.example.jpa3.model.Book;
import com.example.jpa3.model.requestbody.BookRequest;
import com.example.jpa3.model.response.ApiResponse;
import com.example.jpa3.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody BookRequest bookRequest) {
        Book book = bookRepository.createBook(bookRequest);
        ApiResponse<Book> response = new ApiResponse<>(
                "Book created successfully",
                book,
                HttpStatus.CREATED,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        ApiResponse<List<Book>> response = new ApiResponse<>(
                "Books retrieved successfully",
                books,
                HttpStatus.OK,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable UUID id) {
        Book book = bookRepository.getBookById(id);
        if (book != null) {
            ApiResponse<Book> response = new ApiResponse<>(
                    "Book retrieved successfully",
                    book,
                    HttpStatus.OK,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Book> response = new ApiResponse<>(
                    "Book not found",
                    null,
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByTitle(@PathVariable String title) {
        List<Book> books = bookRepository.getBooksByTitle(title);
        if (books.isEmpty()) {
            ApiResponse<List<Book>> response = new ApiResponse<>(
                    "No books found",
                    null,
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            ApiResponse<List<Book>> response = new ApiResponse<>(
                    "Books retrieved successfully",
                    books,
                    HttpStatus.OK,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBookById(@PathVariable UUID id, @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookRepository.updateBookById(id, bookRequest);
        if (updatedBook != null) {
            ApiResponse<Book> response = new ApiResponse<>(
                    "Book updated successfully",
                    updatedBook,
                    HttpStatus.OK,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Book> response = new ApiResponse<>(
                    "Book not found",
                    null,
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookById(@PathVariable UUID id) {
        boolean isDeleted = bookRepository.deleteBookById(id);
        if (isDeleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Book deleted successfully",
                    null,
                    HttpStatus.OK,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Book not found",
                    null,
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
