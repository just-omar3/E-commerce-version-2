package org.example.Ecommerce2.User.Mapper;

import org.example.Ecommerce2.User.DTO.UserDTO;
import org.example.Ecommerce2.User.UserModels.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
       return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail());

    }


}
