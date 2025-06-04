package com.lima.api.playlist.application.port.out;

import com.lima.api.playlist.domain.model.Playlist;
import java.util.List;

/**
 * Interface de persistência para a entidade Playlist.
 * Define os contratos que devem ser implementados pelos adaptadores de infraestrutura
 * (como repositórios de banco de dados).
 * Pertence à camada Ports Out na Clean Architecture.
 */
public interface PlaylistPersistencePort {

    /**
     * Salva uma nova playlist no repositório.
     *
     * @param playlist Objeto Playlist a ser salvo.
     * @return Playlist persistida.
     */
    Playlist salvar(Playlist playlist);

    /**
     * Recupera todas as playlists disponíveis no repositório.
     *
     * @return Lista de playlists.
     */
    List<Playlist> buscarTodas();

    /**
     * Busca uma playlist pelo seu nome.
     *
     * @param nome Nome da playlist.
     * @return Objeto Playlist correspondente, se encontrado.
     */
    Playlist buscarPorNome(String nome);

    /**
     * Remove uma playlist com base no nome informado.
     *
     * @param nome Nome da playlist a ser removida.
     */
    void removerPorNome(String nome);
}