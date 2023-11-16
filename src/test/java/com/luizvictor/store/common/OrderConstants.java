package com.luizvictor.store.common;

import com.luizvictor.store.entities.order.dto.OrderDto;
import com.luizvictor.store.entities.order.dto.OrderStatusDto;
import com.luizvictor.store.entities.orderItem.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

public class OrderConstants {
    private static final List<OrderItemDto> listItems = new ArrayList<>();
    public static OrderDto ORDER = new OrderDto(1L, items());
    public static OrderDto EMPTY_ORDER = new OrderDto(1L, new ArrayList<>());
    public static OrderStatusDto ORDER_STATUS_UPDATE = new OrderStatusDto("delivered");
    public static OrderStatusDto INVALID_ORDER_STATUS_UPDATE = new OrderStatusDto("delivered");

    private static List<OrderItemDto> items() {
        OrderItemDto orderItemDto = new OrderItemDto(1L, 2);
        listItems.add(orderItemDto);
        return listItems;
    }
}
