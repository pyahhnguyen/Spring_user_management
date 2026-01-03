package com.example.jwat_g.service;

import com.example.jwat_g.exception.UserAlreadyExistsException;
import com.example.jwat_g.exception.UserNotFoundException;
import com.example.jwat_g.model.User;
import com.example.jwat_g.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.jwat_g.mapper.UserMapper;
import com.example.jwat_g.dto.request.UserCreateRequest;
import com.example.jwat_g.dto.request.UserUpdateRequest;
import com.example.jwat_g.dto.response.UserResponse;
import com.example.jwat_g.dto.response.PagedResponse;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserConverter userConverter;

    public List<UserResponse> getAllUsers() {
        return userMapper.getAllUser().stream()
                .map(userConverter::toResponse)
                .collect(Collectors.toList());
    }

    public PagedResponse<UserResponse> searchUsers(String search, int page, int size) {
        int offset = page * size;
        List<User> users = userMapper.searchUsers(search, offset, size);
        long totalElements = userMapper.countUsers(search);

        List<UserResponse> content = users.stream()
                .map(userConverter::toResponse)
                .collect(Collectors.toList());

        return PagedResponse.of(content, totalElements, page, size);
    }

    public UserResponse getUserById(Long id) {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return userConverter.toResponse(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (userMapper.isEmailExist(request.getEmail())) {
            throw new UserAlreadyExistsException("email", request.getEmail());
        }

        User user = userConverter.toEntity(request);

        if (user.getStatus() == null) {
            user.setStatus("ACTIVE");
        }

        userMapper.insertUser(user);

        User savedUser = userMapper.getUserById(user.getId());

        return userConverter.toResponse(savedUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User existingUser = userMapper.getUserById(id);
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        userConverter.updateEntity(request, existingUser);
        userMapper.updateUser(existingUser);
        return userConverter.toResponse(existingUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userMapper.getUserById(id);
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userMapper.deleteUser(id);
    }
}
