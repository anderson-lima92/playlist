package com.lima.api.playlist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private String titulo;
    private String artista;
    private String album;
    private String ano;
    private String genero;
}
