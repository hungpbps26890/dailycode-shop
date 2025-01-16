package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.services.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cartItemService;

    @PostMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse<Cart>> addCartItemToCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("productId") Long productId,
            @RequestParam Integer quantity
    ) {
        Cart cart = cartItemService.addCartItemToCart(cartId, productId, quantity);

        return ResponseEntity.ok(
                ApiResponse.<Cart>builder()
                        .code(HttpStatus.OK.value())
                        .message("Add product to cart success.")
                        .data(cart)
                        .build()
        );
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse<Cart>> updateCartItemQuantity(
            @PathVariable("cartId") Long cartId,
            @PathVariable("productId") Long productId,
            @RequestParam Integer quantity
    ) {
        Cart cart = cartItemService.updateCartItemQuantity(cartId, productId, quantity);

        return ResponseEntity.ok(
                ApiResponse.<Cart>builder()
                        .code(HttpStatus.OK.value())
                        .message("Update product quantity success.")
                        .data(cart)
                        .build()
        );
    }

    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse<Cart>> deleteCartItemFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("productId") Long productId
    ) {
        Cart cart = cartItemService.deleteCartItemFromCart(cartId, productId);

        return ResponseEntity.ok(
                ApiResponse.<Cart>builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete product from cart success.")
                        .data(cart)
                        .build()
        );
    }


}
