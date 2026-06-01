package org.example.Ecommerce2.Order.OrderService;

import jakarta.transaction.Transactional;
import org.example.Ecommerce2.Order.DTO.OrderItemRequest;
import org.example.Ecommerce2.Order.DTO.OrderRequest;
import org.example.Ecommerce2.Order.OrderModels.Order;
import org.example.Ecommerce2.Order.OrderModels.OrderItems;
import org.example.Ecommerce2.Order.OrderRepository.OrderItemRepository;
import org.example.Ecommerce2.Order.OrderRepository.OrderRepository;
import org.example.Ecommerce2.Product.ProductModels.Product;
import org.example.Ecommerce2.Product.ProductRepository.ProductRepository;
import org.example.Ecommerce2.User.UserModels.User;
import org.example.Ecommerce2.User.UserRepository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository  orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;


        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserId(userId);
    }



    public int getTotalOrders() {
        return orderRepository.findAll().size();
    }


    @Transactional
    public Order createOrder(OrderRequest request, String username) {

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now().toString());
        order.setStatus("PENDING");


        List<OrderItems> itemsToSave = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.items()) {

            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemReq.quantity()) {
                throw new RuntimeException(
                        "Insufficient stock for " + product.getProductName()
                );
            }

            product.setStockQuantity(
                    product.getStockQuantity() - itemReq.quantity()
            );

            productRepository.save(product);

            OrderItems item = new OrderItems();
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());


            item.setUnitPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.quantity()));

            item.setTotalPrice(itemTotal);

            item.setOrder(order);

            itemsToSave.add(item);


            total = total.add(itemTotal);
        }

        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(itemsToSave);

        savedOrder.setItems(new ArrayList<>(itemsToSave));

        return savedOrder;
    }




    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long orderId) {
         orderRepository.deleteById(orderId);
    }



    public void addOrderItems(Order order, List<OrderItems> items) {

        for (OrderItems item : items) {

            item.setOrder(order);

        }

        orderItemRepository.saveAll(items);
    }
    public List<OrderItems> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public boolean isOrderOwner(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return order != null && order.getUser().getUserId().equals(userId);
    }

    public void deleteOrderByUserId(Long userId) {

        orderRepository.deleteByUser_UserId(userId);

    }

}