package com.lima.api.playlist.shared.util;

import com.lima.api.playlist.shared.dto.DataResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

public class ResponseUtil {

    public static ResponseEntity<DataResponseDTO<Object>> handleError(Exception e) {
        DataResponseDTO<Object> errorResponse = new DataResponseDTO<>();
        errorResponse.setData(null);
        errorResponse.setErrors(List.of(e.getClass().getSimpleName()));
        errorResponse.setMessage(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseStatus annotation = e.getClass().getAnnotation(ResponseStatus.class);
        if (annotation != null) {
            status = annotation.value();
        }

        return ResponseEntity.status(status).body(errorResponse);
    }

    public static <T> DataResponseDTO<T> createSuccessResponse(T data, String message) {
        DataResponseDTO<T> response = new DataResponseDTO<>();
        response.setData(data);
        response.setErrors(Collections.emptyList());
        response.setMessage(message);
        return response;
    }
}