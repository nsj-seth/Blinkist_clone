package com.example.library.service.image;

import com.example.library.dto.ImageDto;
import com.example.library.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    ImageDto saveImage(MultipartFile file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
