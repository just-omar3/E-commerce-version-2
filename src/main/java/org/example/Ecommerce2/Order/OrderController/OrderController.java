package org.example.Ecommerce2.Order.OrderController;


import jakarta.validation.Valid;
import org.example.Ecommerce2.Order.DTO.OrderRequest;
import org.example.Ecommerce2.Order.OrderModels.Order;
import org.example.Ecommerce2.Order.OrderModels.OrderItems;
import org.example.Ecommerce2.Order.OrderService.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN') or hasRole('ORDER_MANAGER')")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("""
        hasRole('SUPER_ADMIN') or
        hasRole('ADMIN') or
        hasRole('ORDER_MANAGER') or
        #userId == authentication.principal.userId
    """)
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping
    @PreAuthorize("""
    hasRole('SUPER_ADMIN') or
    hasRole('ADMIN') or
    hasRole('CUSTOMER') or
    hasRole('ORDER_MANAGER')
""")
    public Order createOrder(
            @Valid @RequestBody OrderRequest orderRequest,
            Authentication authentication
    ) {

        String username = authentication.getName();

        return orderService.createOrder(orderRequest, username);
    }


    @GetMapping("/{orderId}/items")
    @PreAuthorize("""
        hasRole('SUPER_ADMIN') or
        hasRole('ADMIN') or
        hasRole('ORDER_MANAGER') or
        @orderService.isOrderOwner(#orderId, authentication.principal.userId)
    """)
    public List<OrderItems> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItemsByOrderId(orderId);
    }
}