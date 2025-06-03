package com.lima.api.playlist.application.port.out;

import com.lima.api.playlist.domain.model.Playlist;
import java.util.List;

public interface PlaylistPersistencePort {
    Playlist salvar(Playlist playlist);
    List<Playlist> buscarTodas();
    Playlist buscarPorNome(String nome);
    void removerPorNome(String nome);
}