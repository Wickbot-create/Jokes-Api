package org.com.resource;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import org.com.model.JokeDTO;
import org.com.model.Joke;
import org.com.service.JokeApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JokeResourceTest {

    @InjectMocks
    private JokeResource jokeResource;

    @Mock
    private JokeApiService jokeApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJokesWithValidCount() throws InterruptedException, ExecutionException {
        // Arrange
        int count = 10;
        Joke joke1 = new Joke(1l, "Why did the chicken cross the road?", "To get to the other side.");
        Joke joke2 = new Joke(2l, "What do you call cheese that isn't yours?", "Nacho cheese.");
        List<Joke> jokes = Arrays.asList(joke1, joke2);
        when(jokeApiService.fetchRandomJokes(count)).thenReturn(jokes);

        // Act
        List<JokeDTO> result = jokeResource.getJokes(count);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1l, result.get(0).getId());
        assertEquals("Why did the chicken cross the road?", result.get(0).getQuestion());
        assertEquals("To get to the other side.", result.get(0).getAnswer());
    }

    @Test
    void testGetJokesWithZeroCount() {
        // Arrange
        int count = 0;

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> jokeResource.getJokes(count));
        assertEquals("The count must be 10 or greater.", exception.getMessage());
    }

    @Test
    void testGetJokesWithNegativeCount() {
        // Arrange
        int count = -5;

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> jokeResource.getJokes(count));
        assertEquals("The count must be 10 or greater.", exception.getMessage());
    }

    @Test
    void testGetJokesWhenServiceFails() throws InterruptedException, ExecutionException {
        // Arrange
        int count = 10;
        when(jokeApiService.fetchRandomJokes(count)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> jokeResource.getJokes(count));
        assertEquals("Failed to fetch jokes.", exception.getMessage());
    }

    @Test
    void testGetJokesReturnsEmptyList() throws InterruptedException, ExecutionException {
        // Arrange
        int count = 10;
        when(jokeApiService.fetchRandomJokes(count)).thenReturn(Collections.emptyList());

        // Act
        List<JokeDTO> result = jokeResource.getJokes(count);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
