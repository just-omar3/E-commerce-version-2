package org.example.Ecommerce2.User.UserService;

import org.example.Ecommerce2.Customer.CustomerProfileRepository;
import org.example.Ecommerce2.Employee.EmployeeProfileRepository;
import org.example.Ecommerce2.Order.OrderRepository.OrderRepository;
import org.example.Ecommerce2.Order.OrderService.OrderService;
import org.example.Ecommerce2.Person.PersonModels.Person;
import org.example.Ecommerce2.Person.PersonRepository.PersonRepository;
import org.example.Ecommerce2.User.Mapper.UserDTOMapper;
import org.example.Ecommerce2.User.UserModels.ProfileType;
import org.example.Ecommerce2.User.UserModels.User;
import org.example.Ecommerce2.User.DTO.UserDTO;
import org.example.Ecommerce2.User.UserRepository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDTOMapper userDTOMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private  final EmployeeProfileRepository employeeProfileRepository;
    private final OrderService orderService;


    public UserService(UserDTOMapper userDTOMapper, UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       PersonRepository personRepository,
                       CustomerProfileRepository customerProfileRepository,
                       EmployeeProfileRepository employeeProfileRepository,
                       OrderRepository orderRepository, OrderService orderService) {
        this.userDTOMapper = userDTOMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.orderService = orderService;
    }

    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id).map(userDTOMapper);
    }

    public Optional<UserDTO> getUser(String username, String password) {
        return userRepository.findByUserNameAndPassword(username, password).map(userDTOMapper);
    }

    public Optional<User> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(userDTOMapper).collect(Collectors.toList());
    }

    public User addUser(User user) {
        if (user.getPerson() != null && user.getPerson().getPersonId() == null) {
            Person savedPerson = personRepository.save(user.getPerson());
            user.setPerson(savedPerson);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getProfileType() == null && user.getRole() != null) {
            String roleName = user.getRole().getRoleName();
            if ("CUSTOMER".equals(roleName)) {
                user.setProfileType(ProfileType.CUSTOMER);
            } else if ("EMPLOYEE".equals(roleName) || "ADMIN".equals(roleName) || "SUPER_ADMIN".equals(roleName)) {
                user.setProfileType(ProfileType.EMPLOYEE);
            }
        }

        return userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        user.setUserId(id);

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getPerson() != null) {
            if (user.getPerson().getPersonId() == null) {
                Person savedPerson = personRepository.save(user.getPerson());
                user.setPerson(savedPerson);
            } else {
                personRepository.save(user.getPerson());
            }
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (user.getProfileType() == ProfileType.CUSTOMER) {
            orderService.deleteOrderByUserId(userId);
            customerProfileRepository.deleteById(userId);
        } else if (user.getProfileType() == ProfileType.EMPLOYEE) {
            employeeProfileRepository.deleteById(userId);
        }

        userRepository.delete(user);
    }
}
