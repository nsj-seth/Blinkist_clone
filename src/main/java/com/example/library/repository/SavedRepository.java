package com.example.library.repository;

import com.example.library.model.Saved;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedRepository extends JpaRepository<Saved, Long>{

    Optional<Saved> findByLibraryId(Long libraryId);

}
