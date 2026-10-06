package br.com.ricvon.imageliteapi.application.jwt;

import org.springframework.stereotype.Service;

import br.com.ricvon.imageliteapi.domain.AccessToken;
import br.com.ricvon.imageliteapi.domain.entity.User;

@Service 
public class JwtService{

    public AccessToken generateToken(User user) {
        return new AccessToken("");
    }
}