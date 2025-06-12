package com.example.library.service.user;

import com.example.library.dto.UserDto;
import com.example.library.model.User;
import com.example.library.requests.CreateUserRequest;
import com.example.library.requests.UserUpdateRequest;

public interface IUserService {
    User getUserById(long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
    void setupMapper();

}
