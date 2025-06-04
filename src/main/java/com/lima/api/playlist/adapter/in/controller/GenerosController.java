package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.service.SpotifyService;
import com.lima.api.playlist.shared.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
@RequiredArgsConstructor
@Tag(name = "Gêneros", description = "Endpoint para consultar gêneros musicais vindos da Spotify")
public class GenerosController {

    private final SpotifyService spotifyService;

    @Operation(summary = "Listar gêneros musicais", description = "Retorna uma lista de gêneros musicais disponíveis a partir da API do Spotify.")
    @GetMapping
    public ResponseEntity<?> listarGeneros() {
        try {
            var generos = spotifyService.obterGeneros();
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(generos, "Gêneros musicais obtidos da Spotify."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }
}