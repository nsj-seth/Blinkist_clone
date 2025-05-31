package com.example.library.controller;

import com.example.library.response.ApiResponse;
import com.example.library.service.book.BookService;
import com.example.library.model.book.Book;
import com.example.library.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${api.prefix}/books")
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {this.bookService = bookService;}


    // Get all books
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        return ResponseEntity.ok(new ApiResponse("success", books));
    }

//    // Get a book by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
//        return bookService.getBookById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Add a new book
//    @PostMapping
//    public Book addBook(@RequestBody Book book) {
//        return bookService.addBook(book);
//    }
//
//    // Update an existing book
//    @PutMapping("/{id}")
//    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
//        try {
//            Book updated = bookService.updateBook(id, updatedBook);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Delete a book by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//        bookService.deleteBook(id);
//        return ResponseEntity.noContent().build();
//    }
}