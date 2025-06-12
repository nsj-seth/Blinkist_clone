package com.example.library.controller;

import com.example.library.dto.LibraryDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Library;
import com.example.library.repository.UserRepository;
import com.example.library.response.ApiResponse;
import com.example.library.service.library.ILibraryService;
import com.example.library.service.library.LibraryService;
import com.example.library.service.saved.ISavedService;
import com.example.library.service.saved.SavedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/library")
public class LibraryController {
    private final ILibraryService libraryService;
    private final ISavedService savedService;
    private final UserRepository userRepository;

    @GetMapping("/get/{libraryId}")
    public ResponseEntity<ApiResponse> getLibrary(@PathVariable Long libraryId) {
        try {
            Library library = libraryService.getLibrary(libraryId);
            LibraryDto libraryDto = libraryService.convertLibraryToDto(library);
            return ResponseEntity.ok(new ApiResponse("success", libraryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse> createLibrary(@RequestParam Long userId) {
//        Library library = libraryService.createLibraryForUser(userRepository.findById(userId).get());
//        Library library = new Library();
//     library.setUser(userRepository.findById(userId).get());
//        return ResponseEntity.ok(new ApiResponse("Library created successfully", library));
//    }




}
