package com.dev.shop.mappers.order;

import com.dev.shop.domain.dtos.responses.OrderResponse;
import com.dev.shop.domain.entities.Order;

public interface IOrderMapper {
    OrderResponse toOrderResponse(Order order);
}
