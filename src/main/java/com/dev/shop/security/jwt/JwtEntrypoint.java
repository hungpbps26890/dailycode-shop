package com.dev.shop.security.jwt;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpStatus.UNAUTHORIZED.value());
//        body.put("error", "Unauthorized");
//        body.put("message", authException.getMessage());
//        body.put("path", request.getServletPath());

        ApiResponse<?> body = ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(ErrorMessage.UNAUTHENTICATED)
                .build();

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
