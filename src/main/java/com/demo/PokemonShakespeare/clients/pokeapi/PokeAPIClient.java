package com.demo.PokemonShakespeare.clients.pokeapi;

import com.demo.PokemonShakespeare.clients.pokeapi.models.Pokemon;
import com.demo.PokemonShakespeare.exception.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PokeAPIClient {

    @Value("${pokemon.api.url}")
    private String pokemonApiUrl;

    private RestTemplate restTemplate;

    @Autowired
    public PokeAPIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*public Pokemon getPokemon(String pokemonName) {
        try{
            final String uri = String.format("%s/%s", pokemonApiUrl, pokemonName);
            //ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            //System.out.println("Raw JSON: " + response.getBody()); // DEBUG: Controlla il JSON ricevuto
            Pokemon pokemon = restTemplate.getForObject(uri, Pokemon.class);
            System.out.println("Deserialized Pokemon: " + pokemon); // DEBUG: Controlla se flavor_text_entries è valorizzato

            return pokemon;
        }catch (HttpClientErrorException.NotFound e) {
            throw new PokemonNotFoundException("Pokemon not found: " + pokemonName);
        } catch (RestClientException e) {
            throw new RuntimeException("Error while calling PokeAPI", e);
        }

    }*/

    public Pokemon getPokemon(String pokemonName) {
        try {
            final String uri = String.format("%s/%s", pokemonApiUrl, pokemonName);
            Pokemon pokemon = restTemplate.getForObject(uri, Pokemon.class);
            return pokemon;
        } catch (HttpClientErrorException e) {
            throw new PokemonNotFoundException("Pokemon not found: " + pokemonName);
        } catch (RestClientException e) {
            throw new RuntimeException("Error while calling PokeAPI", e);
        }
    }


}
