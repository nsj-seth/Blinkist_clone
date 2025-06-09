package com.example.library.repository;

import com.example.library.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByBookId(Long id);
}
