package com.dev.shop.services.cart;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.domain.entities.CartItem;
import com.dev.shop.domain.entities.Product;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.repositories.CartItemRepository;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;

    private final ICartService cartService;

    private final IProductService productService;

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CART_ITEM_NOT_FOUND));
    }

    @Transactional
    @Override
    public Cart addCartItemToCart(Long cartId, Long productId, Integer quantity) {
        // 1. Get cart
        Cart cart = cartService.getCart(cartId);

        // 2. Get product
        Product product = productService.getProductById(productId);

        // 3. Check if the product is already in the cart
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() != null) {
            // 4. If yes, increase the quantity of the cart item
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // 5. If no, initiate new cart item and set value for its fields
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }

        cartItem.setTotalPrice();

        cart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart updateCartItemQuantity(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = getCartItem(cartId, productId);

        if (quantity == 0) {
            return deleteCartItemFromCart(cartId, productId);
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();

        cart.updateTotal();

        cartItemRepository.save(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart deleteCartItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = getCartItem(cartId, productId);

        cart.removeCartItem(cartItem);

        cartItemRepository.delete(cartItem);

        return cartRepository.save(cart);
    }
}
