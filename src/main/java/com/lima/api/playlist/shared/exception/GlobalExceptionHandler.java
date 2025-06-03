package com.lima.api.playlist.shared.exception;

import com.lima.api.playlist.shared.dto.DataResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponseDTO<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação nos campos enviados", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DataResponseDTO<?>> handleJsonParseException(HttpMessageNotReadableException ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro ao interpretar o corpo da requisição",
                Collections.singletonList(ex.getMostSpecificCause().getClass().getSimpleName())
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DataResponseDTO<?>> handleNotFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado: " + request.getRequestURI(),
                Collections.singletonList("NoHandlerFoundException")
        );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<DataResponseDTO<?>> handleMissingHeaderException(MissingRequestHeaderException ex) {
        String message = "O header obrigatório '" + ex.getHeaderName() + "' não foi enviado.";
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                Collections.singletonList("MissingRequestHeaderException")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponseDTO<?>> handleGenericException(Exception ex) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro inesperado",
                Collections.singletonList(ex.getClass().getSimpleName())
        );
    }

    private ResponseEntity<DataResponseDTO<?>> buildErrorResponse(HttpStatus status, String message, List<String> errors) {
        DataResponseDTO<Object> response = new DataResponseDTO<>();
        response.setData(null);
        response.setMessage(message);
        response.setErrors(errors);
        return ResponseEntity.status(status).body(response);
    }
}