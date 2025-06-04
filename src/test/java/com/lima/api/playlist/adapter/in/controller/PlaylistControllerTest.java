package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.port.in.PlaylistUseCase;
import com.lima.api.playlist.domain.model.Playlist;
import com.lima.api.playlist.domain.model.Song;
import com.lima.api.playlist.shared.dto.DataResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistControllerTest {

    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlaylistUseCase playlistUseCase;

    private UriComponentsBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uriBuilder = UriComponentsBuilder.newInstance();
    }

    @Test
    void criar_Sucesso() {
        List<Song> musicas = List.of(
                new Song("Bohemian Rhapsody", "Queen", "A Night at the Opera", "1975", "Rock")
        );

        Playlist input = new Playlist("Rock Brasil", "Playlist de rock nacional", musicas);
        Playlist salvo = new Playlist("Rock Brasil", "Playlist de rock nacional", musicas);

        when(playlistUseCase.criarPlaylist(input)).thenReturn(salvo);

        ResponseEntity<?> response = playlistController.criar(input, uriBuilder);

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getHeaders().getLocation().toString().contains("/lists/Rock%20Brasil"));

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Playlist criada com sucesso.", body.getMessage());
        assertTrue(body.getErrors() == null || body.getErrors().isEmpty());

        Playlist result = (Playlist) body.getData();
        assertEquals("Rock Brasil", result.getNome());
        assertEquals("Playlist de rock nacional", result.getDescricao());
        assertEquals(1, result.getMusicas().size());
        assertEquals("Queen", result.getMusicas().get(0).getArtista());
    }


    @Test
    void criar_CampoNomeVazio() {
        Playlist playlist = new Playlist();
        playlist.setNome("   ");

        ResponseEntity<?> response = playlistController.criar(playlist, uriBuilder);

        assertEquals(400, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("O campo 'nome' é obrigatório", body.getMessage());
    }

    @Test
    void criar_ErroInterno() {
        List<Song> musicas = List.of(
                new Song("Samba de Janeiro", "Bellini", "Samba Hits", "1997", "Samba")
        );
        Playlist playlist = new Playlist("Samba", "Músicas de samba", musicas);

        when(playlistUseCase.criarPlaylist(any())).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<?> response = playlistController.criar(playlist, uriBuilder);

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Erro ao salvar", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }

    @Test
    void listar_Sucesso() {
        List<Song> musicas = List.of(
                new Song("Chill Vibes", "LoFi Artist", "LoFi Album", "2022", "Lo-Fi"),
                new Song("Funk You", "MC Sample", "Funk Album", "2021", "Funk")
        );

        List<Playlist> playlists = List.of(
                new Playlist("Chill", "Relaxamento", List.of(musicas.get(0))),
                new Playlist("Funk", "Músicas animadas", List.of(musicas.get(1)))
        );

        when(playlistUseCase.listarPlaylists()).thenReturn(playlists);

        ResponseEntity<?> response = playlistController.listar();

        assertEquals(200, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Listagem de playlists realizada com sucesso.", body.getMessage());

        @SuppressWarnings("unchecked")
        List<Playlist> result = (List<Playlist>) body.getData();
        assertEquals(2, result.size());
        assertEquals("Chill", result.get(0).getNome());
        assertEquals("Funk", result.get(1).getNome());
    }

    @Test
    void listar_ErroInterno() {
        when(playlistUseCase.listarPlaylists()).thenThrow(new RuntimeException("Falha ao listar"));

        ResponseEntity<?> response = playlistController.listar();

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Falha ao listar", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }

    @Test
    void buscar_Sucesso() {
        List<Song> musicas = List.of(
                new Song("Take Me Out", "Franz Ferdinand", "Franz Ferdinand", "2004", "Indie Rock")
        );

        Playlist playlist = new Playlist("Indie", "Indie Rock", musicas);

        when(playlistUseCase.buscarPorNome("Indie")).thenReturn(playlist);

        ResponseEntity<?> response = playlistController.buscar("Indie");

        assertEquals(200, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Playlist encontrada.", body.getMessage());

        Playlist result = (Playlist) body.getData();
        assertEquals("Indie", result.getNome());
        assertEquals("Indie Rock", result.getDescricao());
        assertEquals(1, result.getMusicas().size());
        assertEquals("Franz Ferdinand", result.getMusicas().get(0).getArtista());
    }

    @Test
    void buscar_ErroInterno() {
        when(playlistUseCase.buscarPorNome("Pagode")).thenThrow(new RuntimeException("Não encontrado"));

        ResponseEntity<?> response = playlistController.buscar("Pagode");

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Não encontrado", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }

    @Test
    void remover_Sucesso() {
        doNothing().when(playlistUseCase).removerPlaylist("Eletrônica");

        ResponseEntity<?> response = playlistController.remover("Eletrônica");

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void remover_ErroInterno() {
        doThrow(new RuntimeException("Erro ao remover")).when(playlistUseCase).removerPlaylist("Axé");

        ResponseEntity<?> response = playlistController.remover("Axé");

        assertEquals(500, response.getStatusCodeValue());

        DataResponseDTO<?> body = (DataResponseDTO<?>) response.getBody();
        assertEquals("Erro ao remover", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }
}