package com.luizvictor.course.entities.order.dto;

import com.luizvictor.course.entities.order.OrderStatus;
import com.luizvictor.course.entities.orderItem.OrderItemDto;

import java.util.List;

public record OrderDto(Long userId, List<OrderItemDto> items, OrderStatus status) {
}
