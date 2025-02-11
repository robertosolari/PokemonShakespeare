package com.demo.PokemonShakespeare.clients.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlavorTextEntry {
    @JsonProperty("flavor_text")
    private String flavorText;
    @JsonProperty("language")
    private Language language;
    @JsonProperty("version")
    private Version version;

    public FlavorTextEntry() {
    }

    FlavorTextEntry(String flavorText, Language language, Version version) {
        this.flavorText = flavorText;
        this.language = language;
        this.version = version;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
