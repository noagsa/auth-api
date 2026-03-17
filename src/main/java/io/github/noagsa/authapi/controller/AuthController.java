package io.github.noagsa.authapi.controller;

import io.github.noagsa.authapi.dto.AuthRequestDTO;
import io.github.noagsa.authapi.dto.AuthResponseDTO;
import io.github.noagsa.authapi.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public AuthResponseDTO registerUser(@RequestBody AuthRequestDTO requestDTO) {

        return authService.register(requestDTO);
    }

    @PostMapping("login")
    public AuthResponseDTO loginUser(@RequestBody AuthRequestDTO requestDTO) {

        return authService.login(requestDTO);
    }
}
