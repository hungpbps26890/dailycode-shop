package com.dev.shop.mappers.cart;

import com.dev.shop.domain.dtos.responses.CartItemResponse;
import com.dev.shop.domain.entities.CartItem;

public interface ICartItemMapper {

    CartItemResponse toCartItemResponse(CartItem cartItem);
}
