package com.dev.shop.services.cart;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.repositories.CartItemRepository;
import com.dev.shop.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public Cart createCart() {
        return cartRepository.save(new Cart());
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CART_NOT_FOUND));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);

        cartItemRepository.deleteAllByCartId(cart.getId());

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotal(Long id) {
        Cart cart = getCart(id);

        return cart.getTotal();
    }
}
