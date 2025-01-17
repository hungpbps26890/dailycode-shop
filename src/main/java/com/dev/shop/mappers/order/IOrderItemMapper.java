package com.dev.shop.mappers.order;

import com.dev.shop.domain.dtos.responses.OrderItemResponse;
import com.dev.shop.domain.entities.OrderItem;

public interface IOrderItemMapper {
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
