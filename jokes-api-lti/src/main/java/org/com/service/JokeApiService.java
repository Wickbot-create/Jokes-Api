package org.com.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.com.client.ExternalJokesApi;
import org.com.model.Joke;
import org.com.model.JokeResponseDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@ApplicationScoped
public class JokeApiService {

    @RestClient
    ExternalJokesApi externalJokesApi;

    private final int BATCH_SIZE = 10;

    // Fetch jokes in batches of 10
    public List<Joke> fetchRandomJokes(int count) throws InterruptedException, ExecutionException {
        List<Joke> jokes = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // Thread pool for concurrent requests
        List<Future<List<Joke>>> futures = new ArrayList<>();

        // Split the count into batches of 10
        for (int i = 0; i < count; i += BATCH_SIZE) {
            int batchCount = Math.min(BATCH_SIZE, count - i);
            Future<List<Joke>> future = executor.submit(() -> fetchJokesBatch(batchCount));
            futures.add(future);
        }

        // Collect results from each batch
        for (Future<List<Joke>> future : futures) {
            jokes.addAll(future.get());  // Blocking call to get the batch results
        }

        executor.shutdown();  // Shutdown the executor
        return jokes;
    }

    // Fetch a batch of jokes (size <= 10)
    private List<Joke> fetchJokesBatch(int batchCount) {
        List<Joke> jokesBatch = new ArrayList<>();
        for (int i = 0; i < batchCount; i++) {
            try {
                // Block to get the actual response from the Uni
                JokeResponseDTO response = externalJokesApi.getRandomJoke().await().indefinitely();
                jokesBatch.add(mapToJoke(response));
            } catch (Exception e) {
                e.printStackTrace();  // Handle failure
            }
        }
        return jokesBatch;
    }

    // Map JokeResponseDTO to Joke entity
    private Joke mapToJoke(JokeResponseDTO response) {
        return new Joke(response.getId(), response.getSetup(), response.getPunchline());
    }
}
