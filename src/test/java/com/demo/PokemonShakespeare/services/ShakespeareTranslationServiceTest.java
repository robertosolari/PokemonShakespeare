package com.demo.PokemonShakespeare.services;

import com.demo.PokemonShakespeare.clients.shakespeare.ShakespeareClient;
import com.demo.PokemonShakespeare.clients.shakespeare.models.Contents;
import com.demo.PokemonShakespeare.clients.shakespeare.models.ShakespeareTranslation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShakespeareTranslationServiceTest {

    @Mock
    private ShakespeareClient shakespeareClient;

    @InjectMocks
    private ShakespeareTranslationService shakespeareTranslationService;

    private static final String INPUT_DESCRIPTION = "Hello, world!";
    private static final String TRANSLATED_DESCRIPTION = "Hail, world!";

    private ShakespeareTranslation createMockTranslation() {
        Contents contents = new Contents();
        contents.setTranslated(TRANSLATED_DESCRIPTION);

        ShakespeareTranslation translation = new ShakespeareTranslation();
        translation.setContents(contents);

        return translation;
    }

    @Test
    void testGetTranslation_Success() {
        when(shakespeareClient.getTranslation(INPUT_DESCRIPTION)).thenReturn(createMockTranslation());

        String result = shakespeareTranslationService.getTranslation(INPUT_DESCRIPTION);

        assertNotNull(result);
        assertEquals(TRANSLATED_DESCRIPTION, result);

        verify(shakespeareClient, times(1)).getTranslation(INPUT_DESCRIPTION);
    }

    @Test
    void testGetTranslation_Failure() {
        when(shakespeareClient.getTranslation(INPUT_DESCRIPTION)).thenThrow(new RestClientException("API error"));

        assertThrows(RestClientException.class, () -> shakespeareTranslationService.getTranslation(INPUT_DESCRIPTION));

        verify(shakespeareClient, times(1)).getTranslation(INPUT_DESCRIPTION);
    }
}
