package com.example.library.service.saved;

import com.example.library.dto.SavedDto;
import com.example.library.model.Saved;
import com.example.library.model.SavedItem;

import java.math.BigDecimal;
import java.util.Set;

public interface ISavedService {
    Saved getSaved(long id);
    void clearSaved(long id);
    BigDecimal getTotalCapacity(long id);
    Saved getSavedByUserId(long id);

    Saved getOrCreateSavedByLibraryId(Long libraryId);
    Set<SavedItem> getAllSavedItems(Long libraryId);

    SavedDto convertSavedToDto(Saved saved);
}
