package org.example.Ecommerce2.Customer;

import jakarta.persistence.*;
import org.example.Ecommerce2.User.UserModels.Profile;
import org.example.Ecommerce2.User.UserModels.User;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class CustomerProfile implements Profile {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String defaultShippingAddress;
    private String defaultPaymentMethod;
    private Integer loyaltyPoints = 0;
    private LocalDate dateOfBirth;

    public CustomerProfile() {}

    public CustomerProfile(User user) {
        this.user = user;
    }

    @Override
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDefaultShippingAddress() { return defaultShippingAddress; }
    public void setDefaultShippingAddress(String defaultShippingAddress) { this.defaultShippingAddress = defaultShippingAddress; }

    public String getDefaultPaymentMethod() { return defaultPaymentMethod; }
    public void setDefaultPaymentMethod(String defaultPaymentMethod) { this.defaultPaymentMethod = defaultPaymentMethod; }

    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(Integer loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}