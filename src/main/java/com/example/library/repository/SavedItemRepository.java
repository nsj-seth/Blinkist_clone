package com.example.library.repository;

import com.example.library.model.SavedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedItemRepository extends JpaRepository<SavedItem, Long> {

    Optional<SavedItem> findByBookId(Long bookId);
    List<SavedItem> findAllBySavedId(Long savedId);}
