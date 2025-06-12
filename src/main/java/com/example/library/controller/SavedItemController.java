package com.example.library.controller;

import com.example.library.dto.SavedItemDto;
import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.SavedItem;
import com.example.library.response.ApiResponse;
import com.example.library.service.savedItem.ISavedItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/saved-items")

public class SavedItemController {
    private final ISavedItemService savedItemService;


    @PostMapping("/save/{libraryId}/{bookId}")
    public ResponseEntity<ApiResponse> saveBookToLibrary(
            @PathVariable Long libraryId,
            @PathVariable Long bookId) {
        try {
            SavedItem savedItem = savedItemService.saveBookToSaved(libraryId, bookId);
            SavedItemDto savedItemDto = savedItemService.convertSavedItemToDto(savedItem);
            return ResponseEntity.ok(new ApiResponse("Book saved successfully", savedItemDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ApiResponse> removeBookFromSaved(@PathVariable Long bookId) {
        try {

            savedItemService.removeItemFromSaved(bookId);
            return ResponseEntity.ok(new ApiResponse("Book removed from saved successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}



