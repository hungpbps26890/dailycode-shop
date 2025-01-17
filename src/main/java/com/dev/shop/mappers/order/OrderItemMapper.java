package com.dev.shop.mappers.order;

import com.dev.shop.domain.dtos.responses.OrderItemResponse;
import com.dev.shop.domain.entities.OrderItem;
import com.dev.shop.mappers.product.IProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper implements IOrderItemMapper {

    private final IProductMapper productMapper;

    @Override
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .product(productMapper.toProductResponse(orderItem.getProduct()))
                .build();
    }
}
