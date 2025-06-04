package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.domain.model.User;
import com.lima.api.playlist.infrastructure.security.TokenService;
import com.lima.api.playlist.shared.dto.AuthenticationDTO;
import com.lima.api.playlist.shared.dto.LoginResponseDTO;
import com.lima.api.playlist.shared.dto.RegisterDTO;
import com.lima.api.playlist.shared.repository.UserRepository;
import com.lima.api.playlist.shared.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            var response = new LoginResponseDTO(token);
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(response, "Login realizado com sucesso."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }

    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com login, senha e papel.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        try {
            if (userRepository.findByLogin(data.login()) != null) {
                return ResponseEntity.badRequest().body(
                        ResponseUtil.createSuccessResponse(null, "Usuário já existe")
                );
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(null, data.login(), encryptedPassword, data.role());
            userRepository.save(newUser);

            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(null, "Usuário cadastrado com sucesso."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }
}