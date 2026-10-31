package br.com.ricvon.imageliteapi.application.users;

import org.springframework.stereotype.Component;

import br.com.ricvon.imageliteapi.domain.entity.User;

@Component
public class UserMapper {

    public User mapToUser(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
