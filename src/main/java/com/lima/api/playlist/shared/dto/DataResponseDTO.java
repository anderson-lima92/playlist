package com.lima.api.playlist.shared.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataResponseDTO<T> {
    private T data;
    private String message;
    private List<String> errors;
}