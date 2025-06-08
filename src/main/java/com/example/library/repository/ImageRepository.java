package com.example.library.repository;

import com.example.library.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByBookId(Long id);
}
