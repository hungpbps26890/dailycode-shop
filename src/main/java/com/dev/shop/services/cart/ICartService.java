package com.dev.shop.services.cart;

import com.dev.shop.domain.entities.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotal(Long id);

    Cart getCartByUserId(Long userId);
}
