package com.example.library.repository;

import com.example.library.model.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {
    Optional<BookCollection> findByName(String name);
    boolean existsByName(String name);
}