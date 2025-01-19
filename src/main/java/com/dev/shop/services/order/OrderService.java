package com.dev.shop.services.order;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.*;
import com.dev.shop.domain.enums.OrderStatus;
import com.dev.shop.exceptions.OrderException;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.repositories.OrderRepository;
import com.dev.shop.repositories.ProductRepository;
import com.dev.shop.services.cart.ICartService;
import com.dev.shop.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final ICartService cartService;

    private final ProductRepository productRepository;

    private final UserService userService;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CART_NOT_FOUND));

        Order order = createOrder(cart);

        Set<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(orderItems);
        order.setTotal(calculateTotal(orderItems));

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        User user = userService.getUserById(userId);

        return orderRepository.findByUserId(user.getId());
    }

    private Order createOrder(Cart cart) {
        return Order.builder()
                .user(cart.getUser())
                .orderAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();
    }

    private Set<OrderItem> createOrderItems(Order order, Cart cart) {
        if (cart.getCartItems().isEmpty()) {
            throw new OrderException(ErrorMessage.CART_EMPTY);
        }

        return cart.getCartItems()
                .stream()
                .map(item -> {
                    Product product = item.getProduct();

                    if (product.getInventory() < item.getQuantity())
                        throw new OrderException(product.getName() + ErrorMessage.INVENTORY_OUT_OF_STOCK);

                    product.setInventory(product.getInventory() - item.getQuantity());
                    Product updatedProduct = productRepository.save(product);

                    return OrderItem.builder()
                            .order(order)
                            .product(updatedProduct)
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .totalPrice(item.getTotalPrice())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
