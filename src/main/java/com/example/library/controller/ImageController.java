package com.example.library.controller;

import com.example.library.dto.ImageDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Image;
import com.example.library.response.ApiResponse;
import com.example.library.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam MultipartFile file, @RequestParam Long bookId) {
        try {
            ImageDto imageDto = imageService.saveImage(file, bookId);
            return ResponseEntity.ok(new ApiResponse("Image saved successfully", imageDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error processing request: " + e.getMessage(), null));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
        }

        @PutMapping("/update/{imageId}")
        public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
        Image image = imageService.getImageById(imageId);
        if (image != null) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse("Image updated successfully", null));
}
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Update failed! ", INTERNAL_SERVER_ERROR));
        }

        @DeleteMapping("/delete/{imageId}")
        public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
            try {
                Image image = imageService.getImageById(imageId);
                if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image deleted successfully", null));
                }
            } catch (ResourceNotFoundException e) {
                    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete failed! ", INTERNAL_SERVER_ERROR));
        }


}
