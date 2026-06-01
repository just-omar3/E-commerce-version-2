package org.example.Ecommerce2.Order.DTO;


public record OrderItemRequest (
        Long productId,
        int quantity
) {

}