package org.example.Ecommerce2.AuthManagement.AuthController;

import org.example.Ecommerce2.AuthManagement.AuthModel.LoginRequest;
import org.example.Ecommerce2.AuthManagement.AthuService.AuthService;
import org.example.Ecommerce2.AuthManagement.AthuService.JwtService;
import org.example.Ecommerce2.AuthManagement.AuthModel.RegisterRequest;
import org.example.Ecommerce2.Customer.CustomerProfileService;
import org.example.Ecommerce2.Employee.EmployeeProfileService;
import org.example.Ecommerce2.Person.PersonModels.Person;
import org.example.Ecommerce2.Person.PersonRepository.PersonRepository;
import org.example.Ecommerce2.RoleManagment.Models.Role;
import org.example.Ecommerce2.RoleManagment.Repository.RoleRepository;
import org.example.Ecommerce2.User.UserModels.ProfileType;
import org.example.Ecommerce2.User.UserModels.User;
import org.example.Ecommerce2.User.UserService.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final CustomerProfileService customerProfileService;
    private final EmployeeProfileService employeeProfileService;
    private final PersonRepository personRepository;

    public AuthController(AuthService authService,
                          UserService userService,
                          RoleRepository roleRepository,
                          JwtService jwtService,
                          CustomerProfileService customerProfileService,
                          EmployeeProfileService employeeProfileService,
                          PersonRepository personRepository) {
        this.authService = authService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.customerProfileService = customerProfileService;
        this.employeeProfileService = employeeProfileService;
        this.personRepository = personRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getUserName(), request.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        String roleName = "CUSTOMER";

        Person person = new Person();
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setPhoneNumber(request.phoneNumber());
        Person savedPerson = personRepository.save(person);

        User user = new User();
        user.setUserName(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPerson(savedPerson);
        user.setProfileType(ProfileType.CUSTOMER);

        var role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRole(role);

        User savedUser = userService.addUser(user);

        customerProfileService.createCustomerProfile(savedUser);

        return jwtService.generateToken(savedUser);
    }
}