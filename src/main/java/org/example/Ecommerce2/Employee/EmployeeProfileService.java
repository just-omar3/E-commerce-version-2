package org.example.Ecommerce2.Employee;

import org.example.Ecommerce2.User.UserModels.ProfileType;
import org.example.Ecommerce2.User.UserModels.User;
import org.example.Ecommerce2.User.UserRepository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class EmployeeProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final UserRepository userRepository;

    public EmployeeProfileService(EmployeeProfileRepository employeeProfileRepository,
                                  UserRepository userRepository) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.userRepository = userRepository;
    }

    public EmployeeProfile createCustomerProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileType(ProfileType.CUSTOMER);
        userRepository.save(user);

        EmployeeProfile profile = new EmployeeProfile();
        profile.setUser(user);
        profile.setId(user.getUserId());
        return employeeProfileRepository.save(profile);
    }
    public Optional<EmployeeProfile> getByUserId(Long userId) {
        return employeeProfileRepository.findByUser_UserId(userId);
    }

}