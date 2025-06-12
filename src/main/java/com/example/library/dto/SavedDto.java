package com.example.library.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class SavedDto {
    private Long id;
    private BigDecimal totalCapacity;
    private Set<SavedItemDto> savedItems;

}
