package com.dev.shop.controllers;

import com.dev.shop.domain.dtos.responses.ApiResponse;
import com.dev.shop.domain.dtos.responses.OrderResponse;
import com.dev.shop.domain.entities.Order;
import com.dev.shop.mappers.order.IOrderMapper;
import com.dev.shop.services.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    private final IOrderMapper orderMapper;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestParam Long userId) {
        Order order = orderService.placeOrder(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<OrderResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .message("Place order success.")
                                .data(orderMapper.toOrderResponse(order))
                                .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);

        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get order success.")
                        .data(orderMapper.toOrderResponse(order))
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderByUserId(@PathVariable("userId") Long userId) {
        List<Order> orders = orderService.getOrderByUserId(userId);

        return ResponseEntity.ok(
                ApiResponse.<List<OrderResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Get order success.")
                        .data(orders.stream()
                                .map(orderMapper::toOrderResponse)
                                .toList()
                        )
                        .build()
        );
    }
}
