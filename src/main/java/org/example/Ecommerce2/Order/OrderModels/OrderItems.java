package org.example.Ecommerce2.Order.OrderModels;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Positive;
import org.example.Ecommerce2.Product.ProductModels.Product;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity

public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    @Positive
    private int quantity;


    public OrderItems(Long itemId, Order order, Product product,
                      BigDecimal totalPrice, BigDecimal unitPrice, int quantity) {
        this.itemId = itemId;
        this.product = product;
        this.order = order;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderItems() {

    }


    public BigDecimal getUnitPrice() {

        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {

        this.unitPrice = unitPrice;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }


    public Long getItemId() {
        return itemId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }



    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}