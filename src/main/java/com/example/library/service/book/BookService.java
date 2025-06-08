package com.example.library.service.book;

import com.example.library.dto.BookDto;
import com.example.library.dto.ImageDto;
import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.book.Book;
import com.example.library.model.bookcollection.BookCollection;
import com.example.library.model.image.Image;
import com.example.library.repository.BookCollectionRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ImageRepository;
import com.example.library.requests.AddBookRequest;
import com.example.library.requests.BookUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final BookCollectionRepository bookCollectionRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Transactional
public Book addBook(AddBookRequest request) {
    // Check if book with same title and author exists
    if (bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor()).size() > 0) {
        throw new AlreadyExistsException("Book with title: " + request.getTitle() + " by author: " + request.getAuthor() + " already exists!");
    }

    // Convert collection names to BookCollection entities
    Set<BookCollection> bookCollections = new HashSet<>();
    for (String collectionName : request.getBookCollectionNames()) {
        BookCollection collection = bookCollectionRepository.findByName(collectionName)
            .orElseGet(() -> {
                // If collection doesn't exist, create a new one
                BookCollection newCollection = new BookCollection(collectionName);
                return bookCollectionRepository.save(newCollection);
            });
        bookCollections.add(collection);
    }

    // Create and save the book with collections
    Book book = createBook(request, bookCollections);
    return bookRepository.save(book);
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

    @Override
    public List<BookDto> getConvertedBooks(List<Book> books) {
        return books.stream().map(this::convertToDto).toList();
    }

    @Override
    public BookDto convertToDto(Book book) {
        if (book == null) {
            return null;
        }
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        Image image = imageRepository.findByBookId(book.getId());
        
        if (image != null) {
            ImageDto imageDto = modelMapper.map(image, ImageDto.class);
            bookDto.setImage(imageDto);
        }
        return bookDto;
    }

}