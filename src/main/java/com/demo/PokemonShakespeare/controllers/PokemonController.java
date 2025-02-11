package com.demo.PokemonShakespeare.controllers;

import com.demo.PokemonShakespeare.api.model.PokemonShakespeare;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import com.demo.PokemonShakespeare.services.PokemonService;
import com.demo.PokemonShakespeare.services.ShakespeareTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private PokemonService pokemonService;
    private ShakespeareTranslationService shakespeareTranslationService;

    @Autowired
    public PokemonController(
            PokemonService pokemonService,
            ShakespeareTranslationService shakespeareTranslationService) {
        this.pokemonService = pokemonService;
        this.shakespeareTranslationService = shakespeareTranslationService;
    }

    @GetMapping("/{pokemonName}")
    public ResponseEntity<?> getPokemon(@PathVariable String pokemonName) {
        try {
            PokemonShakespeare response = pokemonService.getPokemonShakespeareDescription(pokemonName);
            return ResponseEntity.ok(response);
        } catch (PokemonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
