package com.example.book_inventory.service;

import com.example.book_inventory.model.Book;
import com.example.book_inventory.repository.BookRepository;
import com.example.book_inventory.exception.BookNotFoundException;
import com.example.book_inventory.exception.BookAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    // Get a book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    // Add a book
    public Book addBook(Book book) {
        // Check if a book with the same title already exists
        List<Book> existingBooks = bookRepository.findByTitleContainingIgnoreCase(book.getTitle());

        if (!existingBooks.isEmpty()) {
            throw new BookAlreadyExistsException("A book with the title '" + book.getTitle() + "' already exists.");
        }

        return bookRepository.save(book);
    }

    // Update a book (partial update)
    public Book updateBook(Long id, Book bookDetails) {
        // Find the existing book by ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        // Only update the fields that are present in bookDetails
        if (bookDetails.getTitle() != null) {
            book.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getAuthor() != null) {
            book.setAuthor(bookDetails.getAuthor());
        }
        if (bookDetails.getGenre() != null) {
            book.setGenre(bookDetails.getGenre());
        }
        if (bookDetails.getDescription() != null) {
            book.setDescription(bookDetails.getDescription());
        }
        if (bookDetails.getPrice() != null) {
            book.setPrice(bookDetails.getPrice());
        }
        if (bookDetails.getStock() != null) {
            book.setStock(bookDetails.getStock());
        }
        // Save the updated book
        return bookRepository.save(book);
    }


    // Delete a book
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        bookRepository.delete(book);
    }

    // View all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Search books by title, author, genre
    public List<Book> searchBooks(String keyword, String author, String genre) {
        // filtering logic to the repository
        return bookRepository.searchBooks(
                keyword == null || keyword.isEmpty() ? null : keyword,
                author == null || author.isEmpty() ? null : author,
                genre == null || genre.isEmpty() ? null : genre
        );
    }

}
