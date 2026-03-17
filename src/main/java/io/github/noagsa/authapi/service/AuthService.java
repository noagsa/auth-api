package io.github.noagsa.authapi.service;

import io.github.noagsa.authapi.dto.AuthRequestDTO;
import io.github.noagsa.authapi.dto.AuthResponseDTO;
import io.github.noagsa.authapi.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private  Map<String, User> users;

    public AuthService() {
        users = new HashMap<>();
    }

    public AuthResponseDTO register(AuthRequestDTO authRequestDTO) {
        return null;
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        return null;
    }
}
