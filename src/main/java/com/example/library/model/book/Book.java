package com.example.library.model.book;
import com.example.library.model.bookcollection.BookCollection;
import com.example.library.model.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

@Id
@GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int chapters;
    private String description;
    private String about;
    private int year;
    //    private String duration;
//    private Summary summary;


    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "book_collections_mapping",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private Set<BookCollection> bookCollection;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    public Book(String title, String author, int chapters, String description,String about, int year, Set<BookCollection> bookCollection) {
        this.title = title;
        this.author = author;
        this.chapters = chapters;
        this.description = description;
        this.about = about;
        this.year = year;
        this.bookCollection = bookCollection;
    }






    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", chapters='" + chapters + '\'' +
                ", description='" + description + '\'' +
                ", about='" + about + '\'' +
                ", year=" + year +
                '}';
    }
}