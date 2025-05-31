package com.example.library.model.bookcollection;

import com.example.library.model.book.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_collections")
@Getter
@Setter
@NoArgsConstructor
public class BookCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "bookCollection")
    private Set<Book> books = new HashSet<>();

    public BookCollection(String name) {
        this.name = name;
    }
}