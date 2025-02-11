package com.demo.PokemonShakespeare.services;

import com.demo.PokemonShakespeare.api.model.PokemonShakespeare;
import com.demo.PokemonShakespeare.clients.pokeapi.PokeAPIClient;
import com.demo.PokemonShakespeare.clients.pokeapi.models.Pokemon;
import com.demo.PokemonShakespeare.clients.shakespeare.ShakespeareClient;
import com.demo.PokemonShakespeare.clients.shakespeare.models.ShakespeareTranslation;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@EnableCaching
public class PokemonService {
    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    private final PokeAPIClient pokeAPIClient;
    private final ShakespeareClient shakespeareClient;

    @Autowired
    public PokemonService(PokeAPIClient pokeAPIClient, ShakespeareClient shakespeareClient) {
        this.pokeAPIClient = pokeAPIClient;
        this.shakespeareClient = shakespeareClient;
    }

    @Cacheable(value = "pokemonDescriptions", key = "#pokemonName")
    public PokemonShakespeare getPokemonShakespeareDescription(String pokemonName) {
        try {
            logger.info("Fetching Pokémon: {}", pokemonName);
            Pokemon pokemon = pokeAPIClient.getPokemon(pokemonName);

            if (pokemon == null) {
                logger.warn("Pokemon {} not found in PokeAPI", pokemonName);
                throw new PokemonNotFoundException("Pokemon not found: " + pokemonName);
            }

            String description = pokemon.getFlavorTextEntries().stream()
                    .filter(flavorTextEntry -> "en".equals(flavorTextEntry.getLanguage().getName()))
                    .map(flavorTextEntry -> flavorTextEntry.getFlavorText().replaceAll("\n", " "))
                    .findFirst()
                    .orElseThrow(() -> {
                        logger.warn("No English description found for {}", pokemonName);
                        return new PokemonNotFoundException("No English description found for: " + pokemonName);
                    });

            logger.info("Fetched description for {}: {}", pokemonName, description);

            ShakespeareTranslation translation = shakespeareClient.getTranslation(description);
            String shakespeareanDescription = URLDecoder.decode(translation.getContents().getTranslated(), StandardCharsets.UTF_8);

            return new PokemonShakespeare(pokemonName, shakespeareanDescription);
        } catch (PokemonNotFoundException e) {
            logger.error("PokemonNotFoundException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving Pokémon description: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while retrieving Pokémon Shakespearean description", e);
        }
    }
}

