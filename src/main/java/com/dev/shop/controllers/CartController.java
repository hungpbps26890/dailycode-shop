package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.CartResponse;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.mappers.cart.ICartMapper;
import com.dev.shop.services.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    private final ICartMapper cartMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable("id") Long id) {
        Cart cart = cartService.getCart(id);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get cart success.")
                        .data(cartMapper.toCartResponse(cart))
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
