package com.example.book_inventory.controller;

import com.example.book_inventory.model.Book;
import com.example.book_inventory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Add a book (Only Admin with MANAGE_BOOKS permission)
    @PreAuthorize("hasAuthority('MANAGE_BOOKS')")
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // Update a book (Only Admin with MANAGE_BOOKS permission)
    @PreAuthorize("hasAuthority('MANAGE_BOOKS')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    // Delete a book (Only Admin with MANAGE_BOOKS permission)
    @PreAuthorize("hasAuthority('MANAGE_BOOKS')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Get all books (Both Admin and User with VIEW_BOOKS permission)
    @PreAuthorize("hasAuthority('VIEW_BOOKS')")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get a book by ID (Both Admin and User with VIEW_BOOKS permission)
    @PreAuthorize("hasAuthority('VIEW_BOOKS')")
    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Search books by title, author, or genre (Both Admin and User with VIEW_BOOKS permission)
    @PreAuthorize("hasAuthority('VIEW_BOOKS')")
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String author,
                                                  @RequestParam(required = false) String genre) {
        return ResponseEntity.ok(bookService.searchBooks(keyword, author, genre));
    }
}
