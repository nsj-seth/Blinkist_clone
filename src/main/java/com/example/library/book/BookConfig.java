package com.example.library.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
//            Book book1 = new Book("Book 1", "Author 1", "Genre 1", 2025, "Yes", 5, "image.png");
//            Book book2 = new Book("Book 2", "Author 2", "Genre 2", 2025, "Yes", 5, "image2.png");
//
//           bookRepository.save(book1);
//           bookRepository.save(book2);
        };
    }
}
