package com.lima.api.playlist.application.port.in;

import com.lima.api.playlist.domain.model.Playlist;
import java.util.List;

/**
 * Interface que define os casos de uso (regras de negócio) relacionados à entidade Playlist.
 * Faz parte da camada de Application (casos de uso) na Clean Architecture.
 */
public interface PlaylistUseCase {

    /**
     * Cria uma nova playlist a partir dos dados fornecidos.
     *
     * @param playlist Objeto Playlist contendo nome, descrição e músicas.
     * @return Playlist criada com ID gerado e persistida.
     */
    Playlist criarPlaylist(Playlist playlist);

    /**
     * Retorna uma lista com todas as playlists registradas no sistema.
     *
     * @return Lista de objetos Playlist.
     */
    List<Playlist> listarPlaylists();

    /**
     * Busca uma playlist específica pelo seu nome.
     *
     * @param nome Nome da playlist a ser buscada.
     * @return Objeto Playlist correspondente ou lança exceção se não encontrado.
     */
    Playlist buscarPorNome(String nome);

    /**
     * Remove uma playlist existente com base no seu nome.
     *
     * @param nome Nome da playlist a ser removida.
     */
    void removerPlaylist(String nome);
}