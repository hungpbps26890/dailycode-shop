package com.dev.shop.mappers.cart;

import com.dev.shop.domain.dtos.responses.CartResponse;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.domain.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper implements ICartMapper {

    private final ICartItemMapper cartItemMapper;

    @Override
    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .total(cart.getTotal())
                .cartItems(Optional.ofNullable(cart.getCartItems())
                        .map(items -> items.stream()
                                .map(cartItemMapper::toCartItemResponse)
                                .collect(Collectors.toSet())
                        )
                        .orElse(null)
                )
                .quantity(calculateQuantity(cart.getCartItems()))
                .build();
    }

    private Integer calculateQuantity(Set<CartItem> cartItems) {
        return Optional.ofNullable(cartItems)
                .map(items -> items.stream()
                        .map(CartItem::getQuantity)
                        .reduce(0, Integer::sum)
                )
                .orElse(0);
    }
}
