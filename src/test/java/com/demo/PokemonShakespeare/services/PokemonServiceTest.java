package com.demo.PokemonShakespeare.services;

import com.demo.PokemonShakespeare.api.model.PokemonShakespeare;
import com.demo.PokemonShakespeare.clients.pokeapi.PokeAPIClient;
import com.demo.PokemonShakespeare.clients.pokeapi.models.FlavorTextEntry;
import com.demo.PokemonShakespeare.clients.pokeapi.models.Language;
import com.demo.PokemonShakespeare.clients.pokeapi.models.Pokemon;
import com.demo.PokemonShakespeare.clients.shakespeare.ShakespeareClient;
import com.demo.PokemonShakespeare.clients.shakespeare.models.Contents;
import com.demo.PokemonShakespeare.clients.shakespeare.models.ShakespeareTranslation;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokeAPIClient pokeAPIClient;

    @Mock
    private ShakespeareClient shakespeareClient;

    @InjectMocks
    private PokemonService pokemonService;

    private static final String POKEMON_NAME = "pikachu";
    private static final String DESCRIPTION = "When several of these POKéMON gather, their\felectricity could build and cause lightning storms.";
    private static final String SHAKESPEAREAN_DESCRIPTION = "At which hour several of these pokémon gather, their\\felectricity couldst buildeth and cause lightning storms.";

    private Pokemon createMockPokemon() {
        Language english = new Language();
        english.setName("en");

        FlavorTextEntry flavorTextEntry = new FlavorTextEntry();
        flavorTextEntry.setLanguage(english);
        flavorTextEntry.setFlavorText(DESCRIPTION);

        Pokemon pokemon = new Pokemon();
        pokemon.setFlavorTextEntries(List.of(flavorTextEntry));

        return pokemon;
    }

    private ShakespeareTranslation createMockTranslation() {
        Contents contents = new Contents();
        contents.setTranslated(SHAKESPEAREAN_DESCRIPTION);

        ShakespeareTranslation translation = new ShakespeareTranslation();
        translation.setContents(contents);

        return translation;
    }

    @Test
    void testGetPokemonShakespeareDescription_Success() {
        when(pokeAPIClient.getPokemon(POKEMON_NAME)).thenReturn(createMockPokemon());
        when(shakespeareClient.getTranslation(DESCRIPTION)).thenReturn(createMockTranslation());

        PokemonShakespeare result = pokemonService.getPokemonShakespeareDescription(POKEMON_NAME);

        assertNotNull(result);
        assertEquals(POKEMON_NAME, result.getName());
        assertEquals(SHAKESPEAREAN_DESCRIPTION, result.getDescription());

        verify(pokeAPIClient, times(1)).getPokemon(POKEMON_NAME);
        verify(shakespeareClient, times(1)).getTranslation(DESCRIPTION);
    }

    @Test
    void testGetPokemonShakespeareDescription_PokemonNotFound() {
        when(pokeAPIClient.getPokemon(POKEMON_NAME)).thenThrow(new PokemonNotFoundException("Pokemon not found: " + POKEMON_NAME));

        assertThrows(PokemonNotFoundException.class, () -> pokemonService.getPokemonShakespeareDescription(POKEMON_NAME));

        verify(pokeAPIClient, times(1)).getPokemon(POKEMON_NAME);
        verify(shakespeareClient, never()).getTranslation(anyString());
    }

    @Test
    void testGetPokemonShakespeareDescription_NoEnglishDescription() {
        Pokemon pokemonWithoutEnglish = new Pokemon();
        pokemonWithoutEnglish.setFlavorTextEntries(Collections.emptyList());

        when(pokeAPIClient.getPokemon(POKEMON_NAME)).thenReturn(pokemonWithoutEnglish);

        assertThrows(PokemonNotFoundException.class, () -> pokemonService.getPokemonShakespeareDescription(POKEMON_NAME));

        verify(pokeAPIClient, times(1)).getPokemon(POKEMON_NAME);
        verify(shakespeareClient, never()).getTranslation(anyString());
    }

    @Test
    void testGetPokemonShakespeareDescription_UnexpectedError() {
        when(pokeAPIClient.getPokemon(POKEMON_NAME)).thenThrow(new RuntimeException("Unexpected API error"));

        assertThrows(ResponseStatusException.class, () -> pokemonService.getPokemonShakespeareDescription(POKEMON_NAME));

        verify(pokeAPIClient, times(1)).getPokemon(POKEMON_NAME);
        verify(shakespeareClient, never()).getTranslation(anyString());
    }
}
