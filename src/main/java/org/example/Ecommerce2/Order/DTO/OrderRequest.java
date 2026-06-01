package org.example.Ecommerce2.Order.DTO;

import jakarta.validation.Valid;

import java.util.List;

public record OrderRequest (
       @Valid Long userId
        ,List<@Valid OrderItemRequest> items
) {


}