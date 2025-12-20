package com.example.jwat_g.service;

import com.example.jwat_g.exception.UserAlreadyExistsException;
import com.example.jwat_g.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.jwat_g.mapper.UserMapperDb;
import com.example.jwat_g.dto.mapper.UserMapper;
import com.example.jwat_g.dto.request.UserCreateRequest;
import com.example.jwat_g.dto.request.UserUpdateRequest;
import com.example.jwat_g.dto.response.UserResponse;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapperDb userMapper; // Database mapper
    private final UserMapper dtoMapper; // DTO mapper       

    public List<UserResponse> getAllUsers() {
        return userMapper.getAllUser().stream()
            .map(dtoMapper::toResponse)
            .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return dtoMapper.toResponse(user);
    }

 
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserCreateRequest request) {
        // Validation input
        if (request == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (userMapper.isUsernameExist(request.getUsername())) {
            throw new UserAlreadyExistsException("username", request.getUsername());
        }
        
        if (userMapper.isEmailExist(request.getEmail())) {
            throw new UserAlreadyExistsException("email", request.getEmail());
        }
        
        // Convert dto to entity
        User user = dtoMapper.toEntity(request);
        
        if (user.getStatus() == null) {
            user.setStatus("ACTIVE");
        }
    
        userMapper.insertUser(user);

        // update user to get createdAt
        User saveUser = userMapper.getUserById(user.getId());

        return dtoMapper.toResponse(saveUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User existingUser = userMapper.getUserById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + id);
        }

        dtoMapper.updateEntity(request, existingUser);
        userMapper.updateUser(existingUser);
        return dtoMapper.toResponse(existingUser);
    }

    public void deleteUser(Long id) {

        User existingUser = userMapper.getUserById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userMapper.deleteUser(id);

    }

}
