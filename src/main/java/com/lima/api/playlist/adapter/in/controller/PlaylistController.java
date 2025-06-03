package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.port.in.PlaylistUseCase;
import com.lima.api.playlist.domain.model.Playlist;
import com.lima.api.playlist.shared.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistUseCase service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Playlist playlist, UriComponentsBuilder uriBuilder) {
        try {
            if (playlist.getNome() == null || playlist.getNome().isBlank()) {
                return ResponseEntity.badRequest().body(ResponseUtil.createSuccessResponse(null, "O campo 'nome' é obrigatório"));
            }

            Playlist salva = service.criarPlaylist(playlist);

            URI uri = uriBuilder.path("/lists/{nome}").buildAndExpand(salva.getNome()).toUri();

            return ResponseEntity.created(uri)
                    .body(ResponseUtil.createSuccessResponse(salva, "Playlist criada com sucesso."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Playlist> playlists = service.listarPlaylists();
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(playlists, "Listagem de playlists realizada com sucesso."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<?> buscar(@PathVariable String nome) {
        try {
            Playlist playlist = service.buscarPorNome(nome);
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(playlist, "Playlist encontrada."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<?> remover(@PathVariable String nome) {
        try {
            service.removerPlaylist(nome);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }
}
