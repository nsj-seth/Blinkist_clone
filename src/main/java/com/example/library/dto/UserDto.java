package com.example.library.dto;

import com.example.library.model.Library;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private LibraryDto library;
}
