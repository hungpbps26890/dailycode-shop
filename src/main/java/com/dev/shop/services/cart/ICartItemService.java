package com.dev.shop.services.cart;

import com.dev.shop.domain.entities.Cart;
import com.dev.shop.domain.entities.CartItem;

public interface ICartItemService {

    CartItem getCartItem(Long cartId, Long productId);

    Cart addCartItemToCart(Long cartId, Long productId, Integer quantity);

    Cart updateCartItemQuantity(Long cartId, Long productId, Integer quantity);

    Cart deleteCartItemFromCart(Long cartId, Long productId);
}
