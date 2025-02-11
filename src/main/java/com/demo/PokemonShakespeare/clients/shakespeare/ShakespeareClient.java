package com.demo.PokemonShakespeare.clients.shakespeare;

import com.demo.PokemonShakespeare.clients.shakespeare.models.ShakespeareTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ShakespeareClient {

    @Value("${shakespeare.api.url}")
    private String shakespeareApiUrl;

    private RestTemplate restTemplate;

    @Autowired
    public ShakespeareClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ShakespeareTranslation getTranslation(String description) {
        try {
            String encodedText = URLEncoder.encode(description, StandardCharsets.UTF_8);
            String request = String.format("%s?text=%s", shakespeareApiUrl, encodedText);
            return restTemplate.getForObject(request, ShakespeareTranslation.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error encoding URL", e);
        }
    }

}
