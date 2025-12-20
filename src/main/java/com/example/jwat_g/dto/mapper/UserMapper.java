package com.example.jwat_g.dto.mapper;

import org.springframework.stereotype.Component;
import com.example.jwat_g.model.User;
import com.example.jwat_g.dto.request.UserCreateRequest;
import com.example.jwat_g.dto.response.UserResponse;
import com.example.jwat_g.dto.request.UserUpdateRequest;


@Component
public class UserMapper {
    // convert user request to entity

    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }


    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    public void updateEntity(UserUpdateRequest request, User user) {
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
    }
}
