package com.example.library.requests;

import ch.qos.logback.core.Appender;
import com.example.library.model.bookcollection.BookCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Data
public class AddBookRequest {

    private Long id;
    private String title;
    private String author;
    private int chapters;
    private String description;
    private String about;
    private int year;
    private Set<String> bookCollectionNames;


    //    private String duration;
    //    private Summary summary;



}
