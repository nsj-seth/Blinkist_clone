package com.example.library.book;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

@Id
@SequenceGenerator(
        name = "book_sequence",
        sequenceName = "book_sequence",
        allocationSize = 1
)
@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "book_sequence"
)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private int year;
    private String isbn;
    private int copiesAvailable;
    private String coverImageUrl;
    private String description;
    private String duration;
    private String language;

    public Book() {}


    public Book(Long id, String title, String author, String genre, int year, String isbn, int copiesAvailable, String coverImageUrl, String description,  String duration, String language ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isbn = isbn;
        this.copiesAvailable = copiesAvailable;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.duration = duration;
        this.language = language;

}
    public Book( String title, String author, String genre, int year, String isbn, int copiesAvailable, String coverImageUrl, String description,  String duration, String language ) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isbn = isbn;
        this.copiesAvailable = copiesAvailable;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.duration = duration;
        this.language = language;

    }



    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getCopiesAvailable() { return copiesAvailable; }
    public void setCopiesAvailable(int copiesAvailable) { this.copiesAvailable = copiesAvailable; }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    // Alias method for compatibility with BookService
    public int getPublishedYear() { return year; }
    public void setPublishedYear(int publishedYear) { this.year = publishedYear; }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", isbn='" + isbn + '\'' +
                ", copiesAvailable=" + copiesAvailable +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
