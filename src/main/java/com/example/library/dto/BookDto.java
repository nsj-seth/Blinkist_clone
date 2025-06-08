package com.example.library.dto;

import com.example.library.model.bookcollection.BookCollection;
import lombok.Data;

import java.util.Set;
@Data
public class BookDto {
    private Long id;

    private String title;
    private String author;
    private int chapters;
    private String description;
    private String about;
    private int year;
    private Set<BookCollection> bookCollection;
    private ImageDto image;

}
