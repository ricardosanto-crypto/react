package br.com.ricvon.imageliteapi.application.users;

import br.com.ricvon.imageliteapi.domain.service.UserService;
import br.com.ricvon.imageliteapi.application.jwt.JwtService;
import br.com.ricvon.imageliteapi.domain.AccessToken;
import br.com.ricvon.imageliteapi.infra.repository.UserRepository;  
import br.com.ricvon.imageliteapi.domain.entity.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.ricvon.imageliteapi.domain.exception.DuplicatedTupleException;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if (possibleUser != null) {
            throw new DuplicatedTupleException("User already exists");
        }   
        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        User user = getByEmail(email);
        if (user == null) {
            return null;
        }
        
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            return jwtService.generateToken(user);
        }
        return null;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}