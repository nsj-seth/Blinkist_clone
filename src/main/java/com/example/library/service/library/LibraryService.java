package com.example.library.service.library;

import com.example.library.dto.LibraryDto;
import com.example.library.dto.UserDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Library;
import com.example.library.model.Saved;
import com.example.library.model.User;
import com.example.library.repository.LibraryRepository;
import com.example.library.repository.SavedRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {
    private final LibraryRepository libraryRepository;
    private final SavedRepository savedRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Library createLibraryForUser(User user) {
                    Library library = new Library();

                    library.setUser(user);

        Library savedLibrary = libraryRepository.save(library);

                    // Create Saved collection for this library
                    Saved saved = new Saved();
                    saved.setLibrary(savedLibrary);
                    savedLibrary.setSaved(saved);

                    return libraryRepository.save(savedLibrary);

//        Library library = new Library();
//        library.setUser(user);
//        Library savedLibrary = libraryRepository.save(library);
//
//        // Auto-create Saved for this Library
//        Saved saved = new Saved();
//        saved.setLibrary(savedLibrary);
//        savedRepository.save(saved);
//
//        return savedLibrary;
    }

    @Override
    public Library getLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        return library;
    }

    public Library getLibraryByUserId(Long userId) {
        return libraryRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Library not found for user"));
    }

    @Override
    public LibraryDto convertLibraryToDto(Library library) {
        LibraryDto libraryDto = modelMapper.map(library, LibraryDto.class);
        return libraryDto;
    }
}
