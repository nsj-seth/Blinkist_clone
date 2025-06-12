package com.example.library.service.savedItem;

import com.example.library.dto.SavedItemDto;
import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Saved;
import com.example.library.model.SavedItem;
import com.example.library.repository.BookRepository;
import com.example.library.repository.SavedItemRepository;
import com.example.library.repository.SavedRepository;
import com.example.library.service.book.IBookService;
import com.example.library.service.saved.ISavedService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavedItemService implements ISavedItemService{
    private final SavedItemRepository savedItemRepository;
    private final BookRepository bookRepository;
    private final ISavedService savedService;
    private final ModelMapper modelMapper;
//
//    @Override
//    public void addItemToSaved(Long savedId, Long bookId) {
//        Saved saved = savedService.getSaved(savedId);
//        Book book = bookService.getBookById(bookId);
//        SavedItem savedItem = saved.getSavedItems().stream()
//                .filter(savedItem1 -> savedItem1.getBook().getId().equals(bookId))
//                .findFirst()
//                .orElse(new SavedItem());
//    }

    @Override
    public SavedItem saveBookToSaved(Long libraryId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Saved saved = savedService.getOrCreateSavedByLibraryId(libraryId);

        // Check if book is already saved
        if (saved.getSavedItems().stream()
                .anyMatch(item -> item.getBook().getId().equals(bookId))) {
            throw new AlreadyExistsException("Book is already saved");
        }


        SavedItem savedItem = new SavedItem();
        savedItem.setBook(book);
        savedItem.setSaved(saved);

        // Add to saved collection
        saved.addSavedItem(savedItem);

        // Save and return
        return savedItemRepository.save(savedItem);
    }


    @Override
    @Transactional
    public void removeItemFromSaved(Long bookId) {
        SavedItem savedItem = savedItemRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found in saved items"));
        // Get the Saved entity
        Saved saved = savedItem.getSaved();

        // Remove the reference from the book
        Book book = savedItem.getBook();
        if (book != null) {
            book.setSavedItem(null);
        }

        // Remove from the saved collection's set
        saved.getSavedItems().remove(savedItem);


        // Delete the saved item
        savedItemRepository.delete(savedItem);

    }

    @Override
    public SavedItemDto convertSavedItemToDto(SavedItem savedItem) {
        SavedItemDto savedItemDto = modelMapper.map(savedItem, SavedItemDto.class);
        return savedItemDto;
    }
}
