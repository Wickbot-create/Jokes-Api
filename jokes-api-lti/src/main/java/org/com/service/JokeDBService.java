package org.com.service;

import org.com.model.Joke;
import org.com.repository.JokeRepository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JokeDBService {

    @Inject
    JokeRepository jokeRepository;

    public Uni<Joke> saveJokeIfNotPresent(Joke joke) {
        return (Uni<Joke>) jokeRepository.save(joke);
    }
}