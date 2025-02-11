package com.demo.PokemonShakespeare.clients.pokeapi;

import com.demo.PokemonShakespeare.clients.pokeapi.models.Pokemon;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokeAPIClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokeAPIClient pokeAPIClient;

    private static final String POKEMON_NAME = "pikachu";
    private static final String INVALID_POKEMON_NAME = "apokemon123";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testGetPokemon_Success() {
        // Simuliamo un comportamento positivo di RestTemplate
        Pokemon mockPokemon = new Pokemon();  // Crea un oggetto Pokemon simulato (o usa un oggetto esistente)
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class))).thenReturn(mockPokemon);

        // Chiamiamo il metodo e verifichiamo
        Pokemon result = pokeAPIClient.getPokemon(POKEMON_NAME);
        assertNotNull(result, "Il Pokémon non dovrebbe essere null");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Pokemon.class));  // Verifica che getForObject è stato chiamato una volta
    }

    @Test
    public void testGetPokemon_NotFound() {

        HttpClientErrorException notFoundException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class)))
                .thenThrow(notFoundException);

        // Verifica che venga lanciata l'eccezione giusta (PokemonNotFoundException)
        PokemonNotFoundException exception = assertThrows(PokemonNotFoundException.class, () -> {
            pokeAPIClient.getPokemon(INVALID_POKEMON_NAME);  // Passa il nome di un Pokémon non valido
        });

        assertEquals("Pokemon not found: " + INVALID_POKEMON_NAME, exception.getMessage(),
                "Il messaggio dell'eccezione non è corretto");  // Verifica il messaggio dell'eccezione
    }


    @Test
    public void testGetPokemon_RestClientException() {

        when(restTemplate.getForObject(anyString(), eq(Pokemon.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pokeAPIClient.getPokemon(POKEMON_NAME);
        });

        assertTrue(exception.getMessage().contains("Pokemon not found: " + POKEMON_NAME), "Il messaggio dell'eccezione non è corretto");
    }
}

