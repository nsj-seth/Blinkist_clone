package com.example.library.service.saved;

import com.example.library.dto.SavedDto;
import com.example.library.dto.UserDto;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Library;
import com.example.library.model.Saved;
import com.example.library.model.SavedItem;
import com.example.library.model.User;
import com.example.library.repository.LibraryRepository;
import com.example.library.repository.SavedItemRepository;
import com.example.library.repository.SavedRepository;
import com.example.library.service.savedItem.ISavedItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SavedService implements ISavedService {
    private final SavedRepository savedRepository;
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;
    private final SavedItemRepository savedItemRepository;


    @Override
    public Saved getSaved(long id) {
//    Saved saved = savedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Saved Collection Not Found!"));
//    BigDecimal totalCapacity = saved.getTotalCapacity();
//    saved.setTotalCapacity(totalCapacity);
//    return savedRepository.save(saved);
        return savedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Saved Collection Not Found!"));
}

    @Override
    public void clearSaved(long id) {
        Saved saved = savedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Saved Collection Not Found!"));
        saved.getSavedItems().clear();
        savedRepository.save(saved);


    }

    @Override
    public BigDecimal getTotalCapacity(long id) {
        Saved saved = savedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Saved Collection Not Found!"));
        return saved.getTotalCapacity();
    }

    @Override
    public Saved getSavedByUserId(long userId) {
        Library library = libraryRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Library not found for user"));
        return getOrCreateSavedByLibraryId(library.getId());

    }


    @Override
    public Saved getOrCreateSavedByLibraryId(Long libraryId) {
        return savedRepository.findByLibraryId(libraryId)
                .orElseGet(() -> {
                    Library library = libraryRepository.findById(libraryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));

                    Saved saved = new Saved();
                    saved.setLibrary(library);
                    return savedRepository.save(saved);
                });
    }

    @Override
    public Set<SavedItem> getAllSavedItems(Long libraryId) {
        return getOrCreateSavedByLibraryId(libraryId).getSavedItems();
    }

    @Override
    public SavedDto convertSavedToDto(Saved saved) {
        SavedDto savedDto = modelMapper.map(saved, SavedDto.class);
        return savedDto;
    }
}
