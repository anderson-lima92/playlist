package com.lima.api.playlist.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lima.api.playlist.infrastructure.config.SpotifyConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpotifyServiceTest {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private SpotifyConfig config;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper mapper;

    @Captor
    private ArgumentCaptor<HttpEntity<?>> httpEntityCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        spotifyService = new SpotifyService(config);
        TestUtils.setPrivateField(spotifyService, "restTemplate", restTemplate);
        TestUtils.setPrivateField(spotifyService, "mapper", mapper);
    }

    @Test
    void obterGeneros_DeveRetornarListaDeGeneros() throws Exception {
        when(config.getClientId()).thenReturn("clientId");
        when(config.getClientSecret()).thenReturn("clientSecret");
        when(config.getTokenUrl()).thenReturn("https://spotify.com/token");
        when(config.getGenreUrl()).thenReturn("https://spotify.com/genres");

        ObjectNode tokenNode = mock(ObjectNode.class);
        when(tokenNode.get("access_token")).thenReturn(newTextNode("mocked_token"));

        when(restTemplate.exchange(
                eq("https://spotify.com/token"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(JsonNode.class))
        ).thenReturn(ResponseEntity.ok(tokenNode));

        ArrayNode genresNode = mock(ArrayNode.class);
        when(tokenNode.get("genres")).thenReturn(genresNode);

        List<String> generosMock = List.of("rock", "pop", "jazz");
        when(restTemplate.exchange(
                eq("https://spotify.com/genres"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(JsonNode.class))
        ).thenReturn(ResponseEntity.ok(tokenNode));

        when(mapper.convertValue(genresNode, List.class)).thenReturn(generosMock);

        List<String> result = spotifyService.obterGeneros();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(List.of("rock", "pop", "jazz"), result);
    }

    static class TestUtils {
        static void setPrivateField(Object target, String fieldName, Object value) {
            try {
                var field = SpotifyService.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private JsonNode newTextNode(String text) {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("access_token", text);
        return node;
    }
}