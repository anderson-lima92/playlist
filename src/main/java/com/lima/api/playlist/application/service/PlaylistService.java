package com.lima.api.playlist.application.service;

import com.lima.api.playlist.application.port.in.PlaylistUseCase;
import com.lima.api.playlist.application.port.out.PlaylistPersistencePort;
import com.lima.api.playlist.domain.model.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService implements PlaylistUseCase {

    private final PlaylistPersistencePort persistence;

    @Override
    public Playlist criarPlaylist(Playlist playlist) {
        return persistence.salvar(playlist);
    }

    @Override
    public List<Playlist> listarPlaylists() {
        return persistence.buscarTodas();
    }

    @Override
    public Playlist buscarPorNome(String nome) {
        return persistence.buscarPorNome(nome);
    }

    @Override
    public void removerPlaylist(String nome) {
        persistence.removerPorNome(nome);
    }
}