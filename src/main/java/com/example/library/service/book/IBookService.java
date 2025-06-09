package com.example.library.service.book;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.requests.AddBookRequest;
import com.example.library.requests.BookUpdateRequest;

import java.util.List;

public interface IBookService {
    Book addBook(AddBookRequest book);
    Book getBookById(Long id);
    Book updateBook(BookUpdateRequest book, Long id);

    void deleteBookById(Long id);
    List<Book> getAllBooks();
    List<Book> getBooksByCollection(String bookCollection);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByCollectionAndAuthor(String bookCollection, String author);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByTitleAndAuthor(String title, String author);
    List<Book> getBooksByAuthorAndTitle(String author, String title);
    Long countBooksByCollectionAndTitle(String bookCollection, String title);
    Long countBooksByAuthor(String author);
    Long countBooksByCollection(String bookCollection);

    Book saveBook(Book book);

    List<BookDto> getConvertedBooks(List<Book> books);
    BookDto convertToDto(Book book);
}
