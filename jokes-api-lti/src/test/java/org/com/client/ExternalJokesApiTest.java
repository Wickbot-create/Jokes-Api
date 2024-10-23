package org.com.client;
import org.com.model.JokeResponseDTO;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ExternalJokesApiTest {

    @Mock
    ExternalJokesApi externalJokesApi;

    @InjectMocks
    ExternalJokesApiTest externalJokesApiTest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRandomJoke() {
        // Arrange
        JokeResponseDTO expectedJoke = new JokeResponseDTO(1L, "Question?", "Answer.");
        when(externalJokesApi.getRandomJoke()).thenReturn(Uni.createFrom().item(expectedJoke));

        // Act
        JokeResponseDTO actualJoke = externalJokesApi.getRandomJoke().await().indefinitely();

        // Assert
        assertEquals(expectedJoke.getId(), actualJoke.getId());
        assertEquals(expectedJoke.getSetup(), actualJoke.getSetup());
        assertEquals(expectedJoke.getPunchline(), actualJoke.getPunchline());
    }

    @Test
    public void testGetRandomJoke_Failure() {
        // Arrange
        when(externalJokesApi.getRandomJoke()).thenThrow(new RuntimeException("Service Unavailable"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            externalJokesApi.getRandomJoke().await().indefinitely();
        });

        assertEquals("Service Unavailable", exception.getMessage());
    }
}
