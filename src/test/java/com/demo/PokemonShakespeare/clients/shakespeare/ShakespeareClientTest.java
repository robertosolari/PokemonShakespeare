package com.demo.PokemonShakespeare.clients.shakespeare;

import com.demo.PokemonShakespeare.clients.shakespeare.models.Contents;
import com.demo.PokemonShakespeare.clients.shakespeare.models.ShakespeareTranslation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShakespeareClientTest {

    private static final String DESCRIPTION = "Capable of copying an enemy's genetic code to instantly\ftransform itself into a duplicate of the enemy.";
    private static final String TRANSLATED_DESCRIPTION = "Capable of copying an foe's genetic code to instantly\\ftransform itself into a duplicate of the foe.";

    @Mock
    private RestTemplate restTemplate;

    private ShakespeareClient shakespeareClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shakespeareClient = new ShakespeareClient(restTemplate);
    }

    @Test
    void testGetTranslation_Success() {

        ShakespeareTranslation mockTranslation = new ShakespeareTranslation();
        mockTranslation.setContents(new Contents(TRANSLATED_DESCRIPTION));

        when(restTemplate.getForObject(anyString(), eq(ShakespeareTranslation.class)))
                .thenReturn(mockTranslation);

        ShakespeareTranslation result = shakespeareClient.getTranslation(DESCRIPTION);
        assertNotNull(result);
        assertEquals(TRANSLATED_DESCRIPTION, result.getContents().getTranslated());


        verify(restTemplate).getForObject(anyString(), eq(ShakespeareTranslation.class));
    }

    @Test
    void testGetTranslation_ErrorEncodingURL() {

        when(restTemplate.getForObject(anyString(), eq(ShakespeareTranslation.class)))
                .thenThrow(new RuntimeException("Error encoding URL"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shakespeareClient.getTranslation(DESCRIPTION);
        });
        assertEquals("Error encoding URL", exception.getMessage());
    }

    @Test
    void testGetTranslation_HttpClientError() {

        when(restTemplate.getForObject(anyString(), eq(ShakespeareTranslation.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));


        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            shakespeareClient.getTranslation(DESCRIPTION);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}
