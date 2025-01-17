package com.dev.shop.mappers.order;

import com.dev.shop.domain.dtos.responses.OrderResponse;
import com.dev.shop.domain.entities.Order;
import com.dev.shop.domain.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper implements IOrderMapper {

    private final IOrderItemMapper orderItemMapper;

    @Override
    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .total(order.getTotal())
                .quantity(calculateQuantity(order.getOrderItems()))
                .orderAt(order.getOrderAt())
                .status(order.getStatus().name())
                .orderItems(Optional.ofNullable(order.getOrderItems())
                        .map(items -> items
                                .stream()
                                .map(orderItemMapper::toOrderItemResponse)
                                .toList()
                        )
                        .orElse(null)
                )
                .build();
    }

    private Integer calculateQuantity(Set<OrderItem> orderItems) {
        return Optional.ofNullable(orderItems)
                .map(items -> items
                        .stream()
                        .map(OrderItem::getQuantity)
                        .reduce(0, Integer::sum)
                )
                .orElse(0);
    }
}
