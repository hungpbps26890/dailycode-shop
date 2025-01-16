package com.dev.shop.services.cart;

import com.dev.shop.domain.entities.CartItem;

public interface ICartItemService {

    CartItem getCartItem(Long cartId, Long productId);

    void addCartItemToCart(Long cartId, Long productId, Integer quantity);

    void updateCartItemQuantity(Long cartId, Long productId, Integer quantity);

    void deleteCartItemFromCart(Long cartId, Long productId);
}
