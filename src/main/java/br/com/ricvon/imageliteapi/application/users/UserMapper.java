package br.com.ricvon.imageliteapi.application.users;

import org.springframework.stereotype.Component;

import br.com.ricvon.imageliteapi.domain.entity.User;

@Component
public class UserMapper {

    public User mapToUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
