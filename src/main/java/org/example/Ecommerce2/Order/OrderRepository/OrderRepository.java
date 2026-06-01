package org.example.Ecommerce2.Order.OrderRepository;

import org.example.Ecommerce2.Order.OrderModels.Order;
import org.example.Ecommerce2.User.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUser(User user);

    List<Order> findByUser_UserId(Long userId);

    void deleteByUser_UserId(Long UserId);


}
