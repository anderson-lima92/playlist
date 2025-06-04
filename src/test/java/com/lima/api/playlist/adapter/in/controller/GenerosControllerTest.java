package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.service.SpotifyService;
import com.lima.api.playlist.shared.dto.DataResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenerosControllerTest {

    @InjectMocks
    private GenerosController generosController;

    @Mock
    private SpotifyService spotifyService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarGeneros_Sucesso() throws Exception {
        List<String> generos = Arrays.asList("rock", "pop", "jazz");
        when(spotifyService.obterGeneros()).thenReturn(generos);

        ResponseEntity<?> response = generosController.listarGeneros();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Gêneros musicais obtidos da Spotify.", body.getMessage());
        assertTrue(body.getErrors() == null || body.getErrors().isEmpty());

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) body.getData();
        assertEquals(generos, result);
    }

    @Test
    void listarGeneros_Erro() throws Exception {
        when(spotifyService.obterGeneros()).thenThrow(new RuntimeException("Erro na API da Spotify"));

        ResponseEntity<?> response = generosController.listarGeneros();

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Erro na API da Spotify", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }
}