package com.example.library.model.library;

import com.example.library.model.libraryitem.LibraryItem;
import com.example.library.model.user.User;
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
public class Library {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private int capacity;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LibraryItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(LibraryItem item) {
        this.items.add(item);
        item.setLibrary(this);
        updateCapacity();
    }

    public void removeItem(LibraryItem item) {
        this.items.remove(item);
        item.setLibrary(null);
        updateCapacity();
    }

    private void updateCapacity() {
        this.capacity = items.size();
    }
}
