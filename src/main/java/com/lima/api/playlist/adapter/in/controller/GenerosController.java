package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.service.SpotifyService;
import com.lima.api.playlist.shared.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
@RequiredArgsConstructor
public class GenerosController {

    private final SpotifyService spotifyService;

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