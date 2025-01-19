package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.requests.LoginRequest;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.JwtResponse;
import com.dev.shop.services.authentication.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<JwtResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Login success.")
                        .data(authenticationService.login(request))
                        .build()
        );
    }
}
