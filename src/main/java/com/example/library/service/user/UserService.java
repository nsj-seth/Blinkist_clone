package com.example.library.service.user;

import com.example.library.dto.LibraryDto;
import com.example.library.dto.SavedDto;
import com.example.library.dto.SavedItemDto;
import com.example.library.dto.UserDto;
import com.example.library.exceptions.AlreadyExistsException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.*;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.requests.CreateUserRequest;
import com.example.library.requests.UserUpdateRequest;
import com.example.library.service.library.LibraryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final LibraryService libraryService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;  // Add this


    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id "));
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(request.getEmail() + " already exists!");
        }

        // Get the default USER role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        // Set default role
        user.setRoles(new HashSet<>(Set.of(userRole)));

        // First save the user
                    User savedUser = userRepository.save(user);

                    // Then create and set the library
                    Library library = libraryService.createLibraryForUser(savedUser);
                    savedUser.setLibrary(library);

                    // Save again with the library relzationship
                    return userRepository.save(savedUser);


}


    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstname(request.getFirstname());
            existingUser.setLastname(request.getLastname());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "));

        if (user.getLibrary() != null && user.getLibrary().getSaved() != null) {
            // Clear saved items
            user.getLibrary().getSaved().getSavedItems().clear();
        }

        userRepository.delete(user);
    }



    @Override
    public UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(mapper -> {
                    // Map Library to LibraryDto
                    mapper.map(User::getLibrary, UserDto::setLibrary);
                });

        modelMapper.createTypeMap(Library.class, LibraryDto.class)
                .addMappings(mapper -> {
                    // Map Saved to SavedDto
                    mapper.map(Library::getSaved, LibraryDto::setSaved);
                });

        modelMapper.createTypeMap(Saved.class, SavedDto.class)
                .addMappings(mapper -> {
                    // Map SavedItems to SavedItemDto set
                    mapper.map(Saved::getSavedItems, SavedDto::setSavedItems);
                });

        modelMapper.createTypeMap(SavedItem.class, SavedItemDto.class)
                .addMappings(mapper -> {
                    // Map Book to BookDto
                    mapper.map(SavedItem::getBook, SavedItemDto::setBook);
                });
    }

}
