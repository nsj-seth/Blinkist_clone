package com.example.library.model.bookcollection;

import com.example.library.model.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "bookCollection")
    private Set<Book> books = new HashSet<>();

    public BookCollection(String name) {
        this.name = name;
    }
}