package com.lima.api.playlist.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PlaylistEntity {

    @Id
    private String nome;

    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongEntity> musicas;
}