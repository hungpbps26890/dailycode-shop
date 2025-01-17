package com.dev.shop.mappers.user;

import com.dev.shop.domain.dtos.responses.UserResponse;
import com.dev.shop.domain.entities.User;

public interface IUserMapper {
    UserResponse toUserResponse(User user);
}
