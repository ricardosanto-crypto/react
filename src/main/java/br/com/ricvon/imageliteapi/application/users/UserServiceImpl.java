package br.com.ricvon.imageliteapi.application.users;

import br.com.ricvon.imageliteapi.domain.service.UserService;    
import br.com.ricvon.imageliteapi.domain.AccessToken;
import br.com.ricvon.imageliteapi.infra.repository.UserRepository;  
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.ricvon.imageliteapi.domain.entity.User;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if (possibleUser != null) {
            throw new RuntimeException("User already exists");
        }   
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        User user = getByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            // Generate access token (for simplicity, using a random UUID here)
            String token = java.util.UUID.randomUUID().toString();
            return new AccessToken(token);
        }
        throw new RuntimeException("Invalid email or password");
    }
}