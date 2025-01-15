package com.dev.shop.exceptions;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        log.error("Unexpected exception: {}", exception.toString());

        ApiResponse<?> error = ApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorMessage.UNEXPECTED_ERROR)
                .build();

        return ResponseEntity
                .internalServerError()
                .body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(exception.getMessage())
                        .build())
                ;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UploadImageException.class)
    public ResponseEntity<ApiResponse<?>> handleUploadImageException(UploadImageException exception) {
        return ResponseEntity
                .internalServerError()
                .body(
                        ApiResponse.builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(exception.getMessage())
                                .build()
                );
    }
}
