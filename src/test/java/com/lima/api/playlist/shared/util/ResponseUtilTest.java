package com.lima.api.playlist.shared.util;

import com.lima.api.playlist.shared.dto.DataResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseUtilTest {

    @Test
    void createSuccessResponse_deveRetornarResponseValido() {
        String data = "conteúdo";
        String mensagem = "Operação realizada com sucesso";

        DataResponseDTO<String> response = ResponseUtil.createSuccessResponse(data, mensagem);

        assertEquals(data, response.getData());
        assertEquals(mensagem, response.getMessage());
        assertNotNull(response.getErrors());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void handleError_deveRetornar500ComClasseERROPadrao() {
        RuntimeException ex = new RuntimeException("Algo deu errado");

        ResponseEntity<DataResponseDTO<Object>> response = ResponseUtil.handleError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        DataResponseDTO<?> body = response.getBody();
        assertNotNull(body);
        assertEquals("Algo deu errado", body.getMessage());
        assertEquals(List.of("RuntimeException"), body.getErrors());
    }

    @Test
    void handleError_deveRespeitarAnotacaoResponseStatusQuandoPresente() {
        Exception custom = new RecursoNaoEncontradoException("Recurso não existe");

        ResponseEntity<DataResponseDTO<Object>> response = ResponseUtil.handleError(custom);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        DataResponseDTO<?> body = response.getBody();
        assertNotNull(body);
        assertEquals("Recurso não existe", body.getMessage());
        assertEquals(List.of("RecursoNaoEncontradoException"), body.getErrors());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class RecursoNaoEncontradoException extends RuntimeException {
        public RecursoNaoEncontradoException(String msg) {
            super(msg);
        }
    }
}