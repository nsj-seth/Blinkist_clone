package com.example.library.service.bookCollection;

import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.BookCollection;
import com.example.library.repository.BookCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCollectionService implements IBookCollectionService{
    private final BookCollectionRepository bookCollectionRepository;

    @Override
    public BookCollection getBookCollectionById(Long id) {
        return bookCollectionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("BookCollection Not Found!"));
    }

    @Override
    public BookCollection getBookCollectionByName(String name) {
        return bookCollectionRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("BookCollection Not Found!"));
    }

    @Override
    public List<BookCollection> getAllBookCollections() {
        return bookCollectionRepository.findAll();
    }

    @Override
    public void deleteBookCollectionById(Long id) {
        bookCollectionRepository.findById(id)
                .ifPresentOrElse(bookCollectionRepository::delete,
                        () -> {
                    throw new ResourceNotFoundException("BookCollection Not Found!");
                });
    }

    @Override
    public BookCollection addBookCollection(BookCollection bookCollection) {
        return Optional.of(bookCollection).filter(bc -> !bookCollectionRepository.existsByName(bc.getName()))
                .map(bookCollectionRepository :: save).orElseThrow( () -> new AlreadyExistsException(bookCollection.getName()+" already exists! "));
    }

    @Override
    public BookCollection updateBookCollection(BookCollection bookCollection, Long id) {
        return Optional.ofNullable(getBookCollectionById(id))
                .map(oldBookCollection -> {
                    oldBookCollection.setName(bookCollection.getName());
                    return bookCollectionRepository.save(oldBookCollection);
                }).orElseThrow(() -> new ResourceNotFoundException("BookCollection Not Found!") );
    }
}