package io.github.noagsa.authapi.service;

import io.github.noagsa.authapi.exception.EmailAlreadyExistsException;
import io.github.noagsa.authapi.exception.InvalidCredentialsException;
import io.github.noagsa.authapi.dto.AuthRequestDTO;
import io.github.noagsa.authapi.dto.AuthResponseDTO;
import io.github.noagsa.authapi.model.Role;
import io.github.noagsa.authapi.model.User;
import io.github.noagsa.authapi.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final JwtService jwtService;
    private  Map<String, User> users;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
        users = new HashMap<>();
    }

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO) {
        if (users.containsKey(authRequestDTO.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User(users.size() + 1, authRequestDTO.email(), authRequestDTO.password(), Role.USER);
        users.put(user.getEmail(), user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        if (!users.containsKey(authRequest.email())) {
            throw new InvalidCredentialsException("Email or password are wrong");
        }

        if (!users.get(authRequest.email()).getPassword().equals(authRequest.password())) {
            throw new InvalidCredentialsException("Email or password are wrong");
        }

        String token = jwtService.generateToken(authRequest.email());

        return new AuthResponseDTO(token);
    }
}
