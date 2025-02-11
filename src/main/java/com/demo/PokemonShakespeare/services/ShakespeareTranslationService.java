package com.demo.PokemonShakespeare.services;

import com.demo.PokemonShakespeare.clients.shakespeare.ShakespeareClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShakespeareTranslationService {

    private ShakespeareClient shakespeareClient;

    @Autowired
    private ShakespeareTranslationService(ShakespeareClient shakespeareClient) {
        this.shakespeareClient = shakespeareClient;
    }

    public String getTranslation(String description) {
        return shakespeareClient.getTranslation(description).getContents().getTranslated();
    }
}
