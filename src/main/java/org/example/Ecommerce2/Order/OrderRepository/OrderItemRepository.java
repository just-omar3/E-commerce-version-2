package org.example.Ecommerce2.Order.OrderRepository;

import org.example.Ecommerce2.Order.OrderModels.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrder_OrderId(Long orderId);

}
