package org.example.Ecommerce2.Order.OrderModels;


import org.example.Ecommerce2.User.UserModels.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> items;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String orderDate;

    @Column(nullable = false)
    private String status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




    public Long getOrderId() {
        return orderId;
    }


    public List<OrderItems> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }



    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }



    public void setItems(ArrayList<OrderItems> items) {
        this.items = items;
    }

    public  void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}