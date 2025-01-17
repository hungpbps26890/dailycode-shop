package com.dev.shop.services.order;

import com.dev.shop.domain.entities.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);

    Order getOrder(Long id);

    List<Order> getOrderByUserId(Long userId);
}
