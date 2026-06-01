package org.example.Ecommerce2;

import org.example.Ecommerce2.User.UserService.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ECommeceV2Application {

    public static void main(String[] args) {


       SpringApplication.run(ECommeceV2Application.class, args);



    }

}
