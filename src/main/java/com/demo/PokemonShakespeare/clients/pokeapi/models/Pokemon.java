package com.demo.PokemonShakespeare.clients.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    @JsonProperty("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;
    @JsonProperty("name")
    private String name;

    public Pokemon() {
    }

    Pokemon(List<FlavorTextEntry> flavorTextEntries, String name) {
        this.flavorTextEntries = flavorTextEntries;
        this.name = name;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
