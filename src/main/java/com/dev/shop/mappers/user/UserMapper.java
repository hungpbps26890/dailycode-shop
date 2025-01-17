package com.dev.shop.mappers.user;

import com.dev.shop.domain.dtos.requests.UserCreateRequest;
import com.dev.shop.domain.dtos.requests.UserUpdateRequest;
import com.dev.shop.domain.dtos.responses.UserResponse;
import com.dev.shop.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements IUserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastname(user.getLastname())
                .build();
    }

    @Override
    public User toUser(UserCreateRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastname(request.getLastname())
                .build();
    }

    @Override
    public User updateUser(User user, UserUpdateRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastname(request.getLastname());

        return user;
    }
}
