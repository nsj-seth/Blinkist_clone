package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.requests.AddBookRequest;
import com.example.library.requests.BookUpdateRequest;
import com.example.library.response.ApiResponse;
import com.example.library.service.book.BookService;
import com.example.library.model.book.Book;
import com.example.library.service.book.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/books")
public class BookController {

    private final IBookService bookService;


    // Get all books
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        return ResponseEntity.ok(new ApiResponse("success", books));
    }

    // Get a book by ID
    @GetMapping("/get/{bookId}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable Long bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            BookDto bookDto = bookService.convertToDto(book);
            return ResponseEntity.ok(new ApiResponse("success", bookDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBook(@RequestBody AddBookRequest book) {
        try {
            Book addedBook = bookService.addBook(book);
            if (addedBook == null) {
                return ResponseEntity.badRequest().body(new ApiResponse("Failed to add book", null));
            }
            BookDto bookDto = bookService.convertToDto(addedBook);
            return ResponseEntity.ok(new ApiResponse("Book added successfully!", bookDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error processing request: " + e.getMessage(), null));
        }
    }

    // Update an existing book
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookUpdateRequest request, @PathVariable Long bookId) {
        try {
            Book Abook = bookService.updateBook(request, bookId);
            BookDto AbookDto = bookService.convertToDto(Abook);
            return ResponseEntity.ok(new ApiResponse("Book updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    // Delete a book by ID
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBookById(bookId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully!", bookId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/titles/{title}")
    public ResponseEntity<ApiResponse> getBookByTitle(@PathVariable String title) {

        try {
            List<Book> books = bookService.getBooksByTitle(title);
            if (books.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No books found", null));
            }
            List<BookDto> convertedBooks = bookService.getConvertedBooks(books);
            return ResponseEntity.ok(new ApiResponse("success", convertedBooks));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get/collections/{collection}")
    public ResponseEntity<ApiResponse> getBookByCollection(@PathVariable String collection) {

        try {
            List<Book> books = bookService.getBooksByCollection(collection);
            if (books.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No books found", null));
            }
            List<BookDto> convertedBooks = bookService.getConvertedBooks(books);
            return ResponseEntity.ok(new ApiResponse("success", convertedBooks));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get/authors/{author}")
    public ResponseEntity<ApiResponse> getBookByAuthor(@PathVariable String author) {

        try {
            List<Book> books = bookService.getBooksByAuthor(author);
            if (books.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No books found", null));
            }
            List<BookDto> convertedBooks = bookService.getConvertedBooks(books);
            return ResponseEntity.ok(new ApiResponse("success", convertedBooks));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }


    @GetMapping("/count/by-collection")
    public ResponseEntity<ApiResponse> countProductsByCollection(@RequestParam String bookCollection) {
        try {
            var bookCount = bookService.countBooksByCollection(bookCollection);
            return ResponseEntity.ok(new ApiResponse("Books count ", bookCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}