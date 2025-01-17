package com.dev.shop.mappers.user;

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
}
