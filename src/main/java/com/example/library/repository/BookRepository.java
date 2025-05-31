package com.example.library.repository;

import com.example.library.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Correct method for finding by collection name since the field is bookCollection
    List<Book> findByBookCollectionName(String bookCollectionName);

    List<Book> findByAuthor(String author);

    // Updated to use correct property path
    List<Book> findByBookCollectionNameAndAuthor(String bookCollection, String author);

    List<Book> findByTitle(String title);

    List<Book> findByTitleAndAuthor(String title, String author);

    // Updated to use correct property path
    Long countByBookCollectionNameAndTitle(String bookCollection, String title);

    Long countByAuthor(String author);

    List<Book> findByAuthorAndTitle(String author, String title);

    // Updated to use correct property path
    Long countByBookCollectionName(String bookCollection);
}