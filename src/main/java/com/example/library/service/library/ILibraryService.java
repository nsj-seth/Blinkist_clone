package com.example.library.service.library;

import com.example.library.dto.LibraryDto;
import com.example.library.model.Library;
import com.example.library.model.User;

public interface ILibraryService {
    Library createLibraryForUser(User user);
    Library getLibraryByUserId(Long userId);

    Library getLibrary(Long libraryId);
    LibraryDto convertLibraryToDto(Library library);
}
