package com.example.library.service.book;

import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.book.Book;
import com.example.library.model.bookcollection.BookCollection;
import com.example.library.repository.BookCollectionRepository;
import com.example.library.repository.BookRepository;
import com.example.library.requests.AddBookRequest;
import com.example.library.requests.BookUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final BookCollectionRepository bookCollectionRepository;

    @Override
    public Book addBook(AddBookRequest request) {
        //check if the collection is already found in the database
        //if yes, set it as the new book collection
        //but if no, then save it as a new collection
        //then set it as the new book collection

        Set<BookCollection> bookCollections =
                request.getBookCollectionNames()
                .stream()
                .map(name -> bookCollectionRepository.findByName(name)
                        .orElseGet(() -> {
                            BookCollection newCollection = new BookCollection();
                            newCollection.setName(name);
                            return bookCollectionRepository.save(newCollection);
                        }))
                .collect(Collectors.toSet());

        return bookRepository.save(createBook(request, bookCollections));
    }
    //Helper method to help add a book to the database
    private Book createBook(AddBookRequest request, Set<BookCollection> bookCollection) {
        return new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getChapters(),
                request.getDescription(),
                request.getAbout(),
                request.getYear(),
                bookCollection
        );
    }

    @Override
    public Book updateBook(BookUpdateRequest request, Long bookId) {

        return bookRepository.findById(bookId)
                .map(existingBook -> updateExistingBook(existingBook,request))
                .map(bookRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Book Not Found!"));
    }

    //Helper method to help update an existing book in the database
    private Book updateExistingBook(Book existingBook, BookUpdateRequest request) {
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setChapters(request.getChapters());
        existingBook.setDescription(request.getDescription());
        existingBook.setAbout(request.getAbout());
        existingBook.setYear(request.getYear());

        Set<BookCollection> bookCollections = request.getBookCollectionNames()
                .stream()
                .map(name -> bookCollectionRepository.findByName(name)
                        .orElseGet(() -> {
                            BookCollection newCollection = new BookCollection();
                            newCollection.setName(name);
                            return bookCollectionRepository.save(newCollection);
                        }))
                .collect(Collectors.toSet());

        existingBook.setBookCollection(bookCollections);

        return existingBook;
    }



    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book Not Found!"));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.findById(id)
                .ifPresentOrElse(bookRepository::delete,
                        () -> {throw new ResourceNotFoundException("Book Not Found!");});
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByCollection(String bookCollection) {
        return bookRepository.findByBookCollectionName(bookCollection);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> getBooksByCollectionAndAuthor(String bookCollection, String author) {
        return bookRepository.findByBookCollectionNameAndAuthor(bookCollection, author);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBooksByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Override
    public List<Book> getBooksByAuthorAndTitle(String author, String title) {
        return bookRepository.findByAuthorAndTitle(author, title);
    }

    @Override
    public Long countBooksByCollectionAndTitle(String bookCollection, String title) {
        return bookRepository.countByBookCollectionNameAndTitle(bookCollection, title);
    }

    @Override
    public Long countBooksByAuthor(String author) {
        return bookRepository.countByAuthor(author);
    }

    @Override
    public Long countBooksByCollection(String bookCollection) {
        return bookRepository.countByBookCollectionName(bookCollection);
    }

    public Book saveBook(Book book) {
        // Basic validation (optional)
        if (book.getTitle() == null || book.getAuthor() == null) {
            throw new IllegalArgumentException("Book title and author must not be null");
        }

        // Save and return the book
        return bookRepository.save(book);
    }

}
