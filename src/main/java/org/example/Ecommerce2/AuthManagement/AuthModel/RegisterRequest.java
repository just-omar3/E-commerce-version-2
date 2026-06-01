package org.example.Ecommerce2.AuthManagement.AuthModel;

public record RegisterRequest(
        String username,
        String email,
        String password,
        String roleName,
        String firstName,
        String lastName,
        String phoneNumber) {}



