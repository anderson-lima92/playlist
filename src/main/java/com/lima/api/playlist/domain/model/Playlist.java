package com.lima.api.playlist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    private String nome;
    private String descricao;
    private List<Song> musicas;
}
