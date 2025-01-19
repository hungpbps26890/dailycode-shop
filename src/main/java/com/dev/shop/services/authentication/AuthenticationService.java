package com.dev.shop.services.authentication;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.requests.LoginRequest;
import com.dev.shop.domain.dtos.responses.JwtResponse;
import com.dev.shop.exceptions.AuthException;
import com.dev.shop.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateToken(authentication);

            return JwtResponse.builder().token(token).build();
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthException(ErrorMessage.LOGIN_ERROR);
        }
    }
}
