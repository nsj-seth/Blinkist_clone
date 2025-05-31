package com.example.library.service.image;

import com.example.library.dto.ImageDto;
import com.example.library.model.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    ImageDto saveImage(MultipartFile file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
