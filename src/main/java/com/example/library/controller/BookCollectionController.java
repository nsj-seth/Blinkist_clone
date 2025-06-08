package com.example.library.controller;

import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.bookcollection.BookCollection;
import com.example.library.response.ApiResponse;
import com.example.library.service.book.IBookService;
import com.example.library.service.bookCollection.IBookCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book-collections")
public class BookCollectionController {
    private final IBookCollectionService bookCollectionService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBookCollections() {
        try {
            List<BookCollection> bookCollections = bookCollectionService.getAllBookCollections();
            return ResponseEntity.ok(new ApiResponse("success", bookCollections));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No book collections found", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Something went wrong", null));
        }
    }

    @GetMapping("/get/{bookCollectionId}")
    public ResponseEntity<ApiResponse> getBookCollectionById(@org.springframework.web.bind.annotation.PathVariable Long bookCollectionId) {
        try {
            BookCollection bookCollection = bookCollectionService.getBookCollectionById(bookCollectionId);
            return ResponseEntity.ok(new ApiResponse("success", bookCollection));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No book collection found", null));
        }
    }

    @GetMapping("/get/name/{bookCollectionName}")
    public ResponseEntity<ApiResponse> getBookCollectionByName(@org.springframework.web.bind.annotation.PathVariable String bookCollectionName) {
        try {
            BookCollection bookCollection = bookCollectionService.getBookCollectionByName(bookCollectionName);
            return ResponseEntity.ok(new ApiResponse("success", bookCollection));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));        }    }

    @DeleteMapping("/delete/{bookCollectionId}")
public ResponseEntity<ApiResponse> deleteBookCollection(@org.springframework.web.bind.annotation.PathVariable Long bookCollectionId) {
        try {
           bookCollectionService.deleteBookCollectionById(bookCollectionId);
           return ResponseEntity.ok(new ApiResponse("Book collection deleted successfully!", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
}
@PostMapping("/add")
public ResponseEntity<ApiResponse> addBookCollection(@org.springframework.web.bind.annotation.RequestBody BookCollection bookCollection) {
        try {
           BookCollection addedBookCollection = bookCollectionService.addBookCollection(bookCollection);
           return ResponseEntity.ok(new ApiResponse("Book collection added successfully!", addedBookCollection));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
}

@PutMapping("/update/{bookCollectionId}")
public ResponseEntity<ApiResponse> updateBookCollection(@org.springframework.web.bind.annotation.RequestBody BookCollection bookCollection, @org.springframework.web.bind.annotation.PathVariable Long bookCollectionId) {
        try {
           BookCollection updatedBookCollection = bookCollectionService.updateBookCollection(bookCollection, bookCollectionId);
           return ResponseEntity.ok(new ApiResponse("Book collection updated successfully!", updatedBookCollection));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
}
}


