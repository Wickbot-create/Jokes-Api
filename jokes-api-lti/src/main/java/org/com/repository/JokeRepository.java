package org.com.repository;

import org.com.model.Joke;
import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class JokeRepository {

    // Save a new joke or return the existing one if it already exists
    public Uni<Joke> save(Joke joke) {
        Uni<Joke> existingJokeUni = Joke.find("question", joke.getQuestion()).firstResult();
        
        // Check if the joke exists, and if not, save it
        return existingJokeUni
                .onItem().transform(existingJoke -> {
                    if (existingJoke == null) {
                        // If it doesn't exist, persist the joke
                        joke.persist(); // This is a blocking call
                        return joke; // Return the newly saved joke
                    } else {
                        // Return the existing joke
                        return existingJoke;
                    }
                });
    }

    // Find a joke by its ID
    public Uni<Joke> findById(Long id) {
        return Joke.findById(id);
    }

    // Find a joke by its question
    public Uni<Joke> findByQuestion(String question) {
        return Joke.find("question", question).firstResult();
    }
}
