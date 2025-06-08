package com.example.library.config;

import com.example.library.model.book.Book;
import com.example.library.model.bookcollection.BookCollection;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.context.annotation.Configuration
public class BookConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }























//    @Bean
//    @Transactional
//    CommandLineRunner commandLineRunner(
//            BookRepository bookRepository,
//            BookCollectionRepository collectionRepository) {
//        return args -> {
//            // First save all collections
//            BookCollection collection1 = collectionRepository.save(new BookCollection("Volvo"));
//            BookCollection collection2 = collectionRepository.save(new BookCollection("Mazda"));
//            BookCollection collection3 = collectionRepository.save(new BookCollection("Ford"));
//            BookCollection collection4 = collectionRepository.save(new BookCollection("BMW"));
//            BookCollection collection5 = collectionRepository.save(new BookCollection("Henny"));
//
//            // Create sets of collections
//            Set<BookCollection> collections1 = new HashSet<>();
//            collections1.add(collection1);
//            collections1.add(collection2);
//            collections1.add(collection3);
//            collections1.add(collection4);
//
//            Set<BookCollection> collections2 = new HashSet<>();
//            collections2.add(collection5);
//
//            // Create and save books with the collections
//            Book book1 = new Book("Book 1", "Author 1", 22, "w", "hello", 2025, collections1);
//            Book book2 = new Book("Book 2", "Author 2", 45, "e", "hi", 2025, collections2);
//
//            // Save the books
//            bookRepository.save(book1);
//            bookRepository.save(book2);
//        };
//    }
}