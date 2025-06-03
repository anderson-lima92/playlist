package com.lima.api.playlist.application.port.in;

import com.lima.api.playlist.domain.model.Playlist;
import java.util.List;

public interface PlaylistUseCase {
    Playlist criarPlaylist(Playlist playlist);
    List<Playlist> listarPlaylists();
    Playlist buscarPorNome(String nome);
    void removerPlaylist(String nome);
}