package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.requests.UserCreateRequest;
import com.dev.shop.domain.dtos.requests.UserUpdateRequest;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.UserResponse;
import com.dev.shop.domain.entities.User;
import com.dev.shop.mappers.user.IUserMapper;
import com.dev.shop.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final IUserMapper userMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserCreateRequest request) {
        User user = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .message("Create user success.")
                                .data(userMapper.toUserResponse(user))
                                .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get user success.")
                        .data(userMapper.toUserResponse(user))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserUpdateRequest request
    ) {
        User updatedUser = userService.updateUser(id, request);

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Update user success.")
                        .data(userMapper.toUserResponse(updatedUser))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete user success.")
                        .build()
        );
    }
}
