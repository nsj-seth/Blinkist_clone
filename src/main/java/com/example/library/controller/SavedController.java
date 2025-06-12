package com.example.library.controller;

import com.example.library.dto.SavedDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Saved;
import com.example.library.response.ApiResponse;
import com.example.library.service.saved.ISavedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/saved")
public class SavedController {
    private final ISavedService savedService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getSavedBooks(@PathVariable long id) {
        try {
            Saved savedBooks = savedService.getSaved(id);
            SavedDto savedDto = savedService.convertSavedToDto(savedBooks);
            return ResponseEntity.ok(new ApiResponse("success", savedDto));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));        }
    }

    @DeleteMapping("/{savedId}/clear")
    public ResponseEntity<ApiResponse> clearSavedBooks(@PathVariable long savedId) {
        try {
            savedService.clearSaved(savedId);
            return ResponseEntity.ok(new ApiResponse("Saved books cleared successfully!", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{savedId}/total-capacity")
    public ResponseEntity<ApiResponse> getTotalCapacity(@PathVariable long savedId) {
        try {
            BigDecimal totalCapacity = savedService.getTotalCapacity(savedId);
            return ResponseEntity.ok(new ApiResponse("success", totalCapacity));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));       }
    }


}
