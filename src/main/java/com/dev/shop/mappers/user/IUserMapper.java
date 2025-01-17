package com.dev.shop.mappers.user;

import com.dev.shop.domain.dtos.requests.UserCreateRequest;
import com.dev.shop.domain.dtos.requests.UserUpdateRequest;
import com.dev.shop.domain.dtos.responses.UserResponse;
import com.dev.shop.domain.entities.User;

public interface IUserMapper {
    UserResponse toUserResponse(User user);

    User toUser(UserCreateRequest request);

    User updateUser(User user, UserUpdateRequest request);
}
