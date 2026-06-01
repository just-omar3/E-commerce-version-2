package org.example.Ecommerce2.User.UserController;

import org.example.Ecommerce2.AuthManagement.AuthModel.CustomUserDetails;
import org.example.Ecommerce2.User.DTO.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.example.Ecommerce2.User.UserModels.User;
import org.example.Ecommerce2.User.UserService.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserDTO> getAllUsers() {

        return userService.getUsers();
    }


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','CUSTOMER_SUPPORT')")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping
    public User createUser(@RequestBody User user) {

        return userService.addUser(user);
    }



    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id,@RequestBody User user) {


        userService.updateUser(id,user);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDTO getCurrentUser(Authentication auth) {
        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
        return userService.findUserById(details.getUserId()).orElseThrow();
    }
}
