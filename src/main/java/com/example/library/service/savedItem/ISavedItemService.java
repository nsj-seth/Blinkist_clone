package com.example.library.service.savedItem;

import com.example.library.dto.SavedItemDto;
import com.example.library.model.SavedItem;

public interface ISavedItemService {
    void removeItemFromSaved(Long bookId);

    SavedItem saveBookToSaved(Long libraryId, Long bookId);
    SavedItemDto convertSavedItemToDto(SavedItem savedItem);
}
