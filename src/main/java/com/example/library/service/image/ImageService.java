package com.example.library.service.image;

import com.example.library.dto.ImageDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Image;
import com.example.library.repository.ImageRepository;
import com.example.library.service.book.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IBookService bookService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No image found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotFoundException("No image found");
                });
    }

    @Override
    public ImageDto saveImage(MultipartFile file, Long productId) {
        Book book = bookService.getBookById(productId);
        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setBook(book);

            // First save to get the ID
            Image savedImage = imageRepository.save(image);

            String downloadUrl = "/api/v1/images/image/download/" + savedImage.getId();
            savedImage.setDownloadUrl(downloadUrl);
            imageRepository.save(savedImage);

            book.setImage(savedImage);
            bookService.saveBook(book);

            ImageDto imageDto = new ImageDto();
            imageDto.setImageId(savedImage.getId());
            imageDto.setImageName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());

            return imageDto;

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}




