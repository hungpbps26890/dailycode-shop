package com.dev.shop.mappers.cart;

import com.dev.shop.domain.dtos.responses.CartResponse;
import com.dev.shop.domain.entities.Cart;

public interface ICartMapper {

    CartResponse toCartResponse(Cart cart);
}
