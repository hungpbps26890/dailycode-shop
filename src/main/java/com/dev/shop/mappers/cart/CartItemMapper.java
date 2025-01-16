package com.dev.shop.mappers.cart;

import com.dev.shop.domain.dtos.responses.CartItemResponse;
import com.dev.shop.domain.entities.CartItem;
import com.dev.shop.mappers.product.IProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper implements ICartItemMapper {

    private final IProductMapper productMapper;

    @Override
    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .totalPrice(cartItem.getTotalPrice())
                .product(productMapper.toProductResponse(cartItem.getProduct()))
                .build();
    }
}
