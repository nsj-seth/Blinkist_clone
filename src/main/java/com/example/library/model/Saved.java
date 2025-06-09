package com.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Saved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "saved", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavedItem> savedItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "library_id")
    private Library library;

    // Helper method to maintain bidirectional relationship
    public void addSavedItem(SavedItem savedItem) {
        savedItems.add(savedItem);
        savedItem.setSaved(this);
    }

    public void removeSavedItem(SavedItem savedItem) {
        savedItems.remove(savedItem);
        savedItem.setSaved(null);
    }
}
