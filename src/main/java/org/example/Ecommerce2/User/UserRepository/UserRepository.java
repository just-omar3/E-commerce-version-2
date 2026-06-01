package org.example.Ecommerce2.User.UserRepository;

import org.example.Ecommerce2.User.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


        Optional<User> findByUserName(String userName);


        Optional<User> findByUserNameAndPassword(String userName, String password);
}
