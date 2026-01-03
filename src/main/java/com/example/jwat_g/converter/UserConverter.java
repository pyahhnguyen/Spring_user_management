package com.example.jwat_g.converter;

import org.springframework.stereotype.Component;
import com.example.jwat_g.model.User;
import com.example.jwat_g.dto.request.UserCreateRequest;
import com.example.jwat_g.dto.response.UserResponse;
import com.example.jwat_g.dto.request.UserUpdateRequest;

@Component
public class UserConverter {

    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
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
