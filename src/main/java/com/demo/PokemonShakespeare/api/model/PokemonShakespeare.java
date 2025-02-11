package com.demo.PokemonShakespeare.api.model;

public class PokemonShakespeare {

    private String name;
    private String description;

    public PokemonShakespeare(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
