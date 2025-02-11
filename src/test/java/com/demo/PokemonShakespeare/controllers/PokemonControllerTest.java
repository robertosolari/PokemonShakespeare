package com.demo.PokemonShakespeare.controllers;

import com.demo.PokemonShakespeare.api.model.PokemonShakespeare;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import com.demo.PokemonShakespeare.services.PokemonService;
import com.demo.PokemonShakespeare.services.ShakespeareTranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {

    @Mock
    private PokemonService pokemonService;

    @Mock
    private ShakespeareTranslationService shakespeareTranslationService;

    @InjectMocks
    private PokemonController pokemonController;

    private static final String VALID_POKEMON = "pikachu";
    private static final String INVALID_POKEMON = "unknown";
    private static final String TRANSLATED_DESCRIPTION = "At which hour several of these pokémon gather, their\\felectricity couldst buildeth and cause lightning storms.";

    @BeforeEach
    void setup() {

    }

    @Test
    void testGetPokemon_Success() {
        PokemonShakespeare mockResponse = new PokemonShakespeare(VALID_POKEMON, TRANSLATED_DESCRIPTION);
        when(pokemonService.getPokemonShakespeareDescription(VALID_POKEMON)).thenReturn(mockResponse);

        ResponseEntity<?> response = pokemonController.getPokemon(VALID_POKEMON);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());

        verify(pokemonService, times(1)).getPokemonShakespeareDescription(VALID_POKEMON);
    }

    @Test
    void testGetPokemon_NotFound() {
        when(pokemonService.getPokemonShakespeareDescription(INVALID_POKEMON)).thenThrow(new PokemonNotFoundException("Pokemon not found"));

        ResponseEntity<?> response = pokemonController.getPokemon(INVALID_POKEMON);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Pokemon not found", response.getBody());

        verify(pokemonService, times(1)).getPokemonShakespeareDescription(INVALID_POKEMON);
    }

    @Test
    void testGetPokemon_InternalServerError() {
        when(pokemonService.getPokemonShakespeareDescription(VALID_POKEMON)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = pokemonController.getPokemon(VALID_POKEMON);

        assertNotNull(response);
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(pokemonService, times(1)).getPokemonShakespeareDescription(VALID_POKEMON);
    }
}
