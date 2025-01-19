package com.dev.shop.services.authentication;

import com.dev.shop.domain.dtos.requests.LoginRequest;
import com.dev.shop.domain.dtos.responses.JwtResponse;

public interface IAuthenticationService {

    JwtResponse login(LoginRequest request);
}
