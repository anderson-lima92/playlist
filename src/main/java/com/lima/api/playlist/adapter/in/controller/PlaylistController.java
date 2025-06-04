package com.lima.api.playlist.adapter.in.controller;

import com.lima.api.playlist.application.port.in.PlaylistUseCase;
import com.lima.api.playlist.domain.model.Playlist;
import com.lima.api.playlist.shared.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
@Tag(name = "Playlists", description = "Endpoints para gerenciamento de playlists")
public class PlaylistController {

    private final PlaylistUseCase service;

    @Operation(summary = "Criar nova playlist", description = "Cria uma nova playlist com as músicas informadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Playlist criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
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

    @Operation(summary = "Listar playlists", description = "Lista todas as playlists cadastradas.")
    @ApiResponse(responseCode = "200", description = "Listagem de playlists realizada com sucesso")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Playlist> playlists = service.listarPlaylists();
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(playlists, "Listagem de playlists realizada com sucesso."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }

    @Operation(summary = "Buscar playlist por nome", description = "Busca uma playlist pelo nome.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playlist encontrada"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    @GetMapping("/{nome}")
    public ResponseEntity<?> buscar(@PathVariable String nome) {
        try {
            Playlist playlist = service.buscarPorNome(nome);
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(playlist, "Playlist encontrada."));
        } catch (Exception e) {
            return ResponseUtil.handleError(e);
        }
    }

    @Operation(summary = "Remover playlist por nome", description = "Remove uma playlist pelo nome informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Playlist removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
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