package org.example.Ecommerce2.Customer;

import org.example.Ecommerce2.User.UserModels.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    @Transactional
    public void createCustomerProfile(User user) {
        CustomerProfile profile = new CustomerProfile(user);

        profile.setLoyaltyPoints(0);

        customerProfileRepository.save(profile);
    }
}