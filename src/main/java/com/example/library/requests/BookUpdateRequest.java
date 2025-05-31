package com.example.library.requests;

import lombok.Data;

import java.util.Set;

@Data
public class BookUpdateRequest {

    private Long id;
private String title;
private String author;
private int chapters;
private String description;
private String about;
private int year;
private Set<String> bookCollectionNames;

}
