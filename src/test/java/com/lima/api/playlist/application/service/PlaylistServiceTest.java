package com.lima.api.playlist.application.service;

import com.lima.api.playlist.application.port.out.PlaylistPersistencePort;
import com.lima.api.playlist.domain.model.Playlist;
import com.lima.api.playlist.domain.model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistPersistencePort persistencePort;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Playlist buildPlaylist(String nome, String descricao) {
        Song song = new Song("Imagine", "John Lennon", "Imagine", "1971", "Rock");
        return new Playlist(nome, descricao, List.of(song));
    }

    @Test
    void criarPlaylist_deveChamarSalvarERetornarPlaylist() {
        Playlist playlist = buildPlaylist("Favoritas", "Minhas músicas favoritas");

        when(persistencePort.salvar(playlist)).thenReturn(playlist);

        Playlist result = playlistService.criarPlaylist(playlist);

        assertNotNull(result);
        assertEquals("Favoritas", result.getNome());
        verify(persistencePort).salvar(playlist);
    }

    @Test
    void listarPlaylists_deveRetornarTodasAsPlaylists() {
        Playlist playlist1 = buildPlaylist("Rock", "Rock internacional");
        Playlist playlist2 = buildPlaylist("Samba", "Samba raiz");

        when(persistencePort.buscarTodas()).thenReturn(List.of(playlist1, playlist2));

        List<Playlist> result = playlistService.listarPlaylists();

        assertEquals(2, result.size());
        verify(persistencePort).buscarTodas();
    }

    @Test
    void buscarPorNome_deveRetornarPlaylistQuandoEncontrada() {
        Playlist playlist = buildPlaylist("Indie", "Indie Rock");

        when(persistencePort.buscarPorNome("Indie")).thenReturn(playlist);

        Playlist result = playlistService.buscarPorNome("Indie");

        assertNotNull(result);
        assertEquals("Indie", result.getNome());
        verify(persistencePort).buscarPorNome("Indie");
    }

    @Test
    void removerPlaylist_deveChamarRemoverPorNome() {
        String nome = "Eletrônica";

        doNothing().when(persistencePort).removerPorNome(nome);

        playlistService.removerPlaylist(nome);

        verify(persistencePort).removerPorNome(nome);
    }
}