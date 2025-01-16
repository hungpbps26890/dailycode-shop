package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.services.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(name = "/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cart>> getCart(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                ApiResponse.<Cart>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get cart success.")
                        .data(cartService.getCart(id))
                        .build()
        );
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                ApiResponse.<BigDecimal>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get cart total success.")
                        .data(cartService.getTotal(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}/clear")
    public ResponseEntity<ApiResponse<?>> clearCart(@PathVariable("id") Long id) {
        cartService.clearCart(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Clear cart success.")
                        .build()
        );
    }
}
