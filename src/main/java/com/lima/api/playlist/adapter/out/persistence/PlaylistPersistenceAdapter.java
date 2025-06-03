package com.lima.api.playlist.adapter.out.persistence;

import com.lima.api.playlist.adapter.out.persistence.entity.PlaylistEntity;
import com.lima.api.playlist.adapter.out.persistence.entity.SongEntity;
import com.lima.api.playlist.adapter.out.persistence.repository.PlaylistRepository;
import com.lima.api.playlist.application.port.out.PlaylistPersistencePort;
import com.lima.api.playlist.domain.model.Playlist;
import com.lima.api.playlist.domain.model.Song;
import com.lima.api.playlist.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaylistPersistenceAdapter implements PlaylistPersistencePort {

    private final PlaylistRepository repository;

    @Override
    public Playlist salvar(Playlist playlist) {
        PlaylistEntity entity = mapToEntity(playlist);
        return mapToDomain(repository.save(entity));
    }

    @Override
    public List<Playlist> buscarTodas() {
        return repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public Playlist buscarPorNome(String nome) {
        return repository.findById(nome)
                .map(this::mapToDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist não encontrada"));
    }

    @Override
    public void removerPorNome(String nome) {
        boolean exists = repository.existsById(nome);
        if (!exists) {
            throw new ResourceNotFoundException("Playlist '" + nome + "' não encontrada");
        }
        repository.deleteById(nome);
    }


    private PlaylistEntity mapToEntity(Playlist playlist) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setNome(playlist.getNome());
        entity.setDescricao(playlist.getDescricao());
        entity.setMusicas(playlist.getMusicas().stream().map(song -> {
            var s = new SongEntity();
            s.setTitulo(song.getTitulo());
            s.setArtista(song.getArtista());
            s.setAlbum(song.getAlbum());
            s.setAno(song.getAno());
            s.setGenero(song.getGenero());
            return s;
        }).collect(Collectors.toList()));
        return entity;
    }

    private Playlist mapToDomain(PlaylistEntity entity) {
        Playlist playlist = new Playlist();
        playlist.setNome(entity.getNome());
        playlist.setDescricao(entity.getDescricao());
        playlist.setMusicas(entity.getMusicas().stream().map(e -> new Song(
                e.getTitulo(), e.getArtista(), e.getAlbum(), e.getAno(), e.getGenero()
        )).collect(Collectors.toList()));
        return playlist;
    }
}