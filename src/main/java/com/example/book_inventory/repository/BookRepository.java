package com.example.book_inventory.repository;

import com.example.book_inventory.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title); // Find books by title
    Optional<Book> findById(Long id); // Find book by ID
    // Custom query to handle combined filtering
    @Query("SELECT b FROM Book b WHERE " +
            "(:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
            "(:genre IS NULL OR LOWER(b.genre) LIKE LOWER(CONCAT('%', :genre, '%')))")
    List<Book> searchBooks(@Param("keyword") String keyword,
                           @Param("author") String author,
                           @Param("genre") String genre);
}
