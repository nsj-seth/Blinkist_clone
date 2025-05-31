package com.example.library.service.bookCollection;

import com.example.library.model.bookcollection.BookCollection;

import java.util.List;

public interface IBookCollectionService {
    BookCollection getBookCollectionById(Long id);
    BookCollection getBookCollectionByName(String name);
    List<BookCollection> getAllBookCollections();
    BookCollection addBookCollection(BookCollection bookCollection);
    BookCollection updateBookCollection(BookCollection bookCollection, Long id);
    void deleteBookCollectionById(Long id);
}
