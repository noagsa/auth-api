package io.github.noagsa.authapi.service;

import io.github.noagsa.authapi.exception.EmailAlreadyExistsException;
import io.github.noagsa.authapi.exception.InvalidCredentialsException;
import io.github.noagsa.authapi.dto.AuthRequestDTO;
import io.github.noagsa.authapi.dto.AuthResponseDTO;
import io.github.noagsa.authapi.model.Role;
import io.github.noagsa.authapi.model.User;
import io.github.noagsa.authapi.repository.UserRepository;
import io.github.noagsa.authapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO) {
        return register(authRequestDTO, Role.USER);
    }

    private AuthResponseDTO register(AuthRequestDTO authRequestDTO, Role role) {
        if (userRepository.findByEmail(authRequestDTO.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(authRequestDTO.password());
        User user = new User(userRepository.countUsers() + 1, authRequestDTO.email(), encodedPassword, role);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        if (userRepository.findByEmail(authRequest.email()).isEmpty()) {
            throw new InvalidCredentialsException("Email or password are wrong");
        }

        User user = userRepository.findByEmail(authRequest.email()).get();

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Email or password are wrong");
        }

        String token = jwtService.generateToken(authRequest.email());

        return new AuthResponseDTO(token);
    }
}
