package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.domain.model.User;
import com.lima.api.playlist.infrastructure.security.TokenService;
import com.lima.api.playlist.shared.dto.AuthenticationDTO;
import com.lima.api.playlist.shared.dto.DataResponseDTO;
import com.lima.api.playlist.shared.dto.LoginResponseDTO;
import com.lima.api.playlist.shared.dto.RegisterDTO;
import com.lima.api.playlist.shared.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        AuthenticationDTO loginData = new AuthenticationDTO("testuser", "password");
        User user = new User(1L, "testuser", "encryptedPassword", "USER");
        String token = "jwt-token";

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn(token);

        ResponseEntity<?> response = authController.login(loginData);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        LoginResponseDTO responseData = (LoginResponseDTO) body.getData();
        assertEquals(token, responseData.token());
        assertEquals("Login realizado com sucesso.", body.getMessage());
    }

    @Test
    void login_Failure() {
        AuthenticationDTO loginData = new AuthenticationDTO("testuser", "wrongPassword");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid login"));

        ResponseEntity<?> response = authController.login(loginData);

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertNotNull(body);
        assertEquals("Invalid login", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }

    @Test
    void register_Success() {
        RegisterDTO registerData = new RegisterDTO("newuser", "123456", "USER");

        when(userRepository.findByLogin("newuser")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<?> response = authController.register(registerData);

        assertEquals(200, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Usuário cadastrado com sucesso.", body.getMessage());
        assertTrue(body.getErrors() == null || body.getErrors().isEmpty());
    }

    @Test
    void register_UserAlreadyExists() {
        RegisterDTO registerData = new RegisterDTO("existingUser", "123456", "USER");
        when(userRepository.findByLogin("existingUser")).thenReturn(new User());

        ResponseEntity<?> response = authController.register(registerData);

        assertEquals(400, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Usuário já existe", body.getMessage());
    }

    @Test
    void register_Failure() {
        RegisterDTO registerData = new RegisterDTO("user", "pass", "USER");

        when(userRepository.findByLogin("user")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<?> response = authController.register(registerData);

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Erro ao salvar", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }
}