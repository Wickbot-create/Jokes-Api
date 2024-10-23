package org.com.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.com.model.JokeDTO;
import org.com.service.JokeApiService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/jokes")
@Produces(MediaType.APPLICATION_JSON)
public class JokeResource {

    @Inject
    JokeApiService jokesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JokeDTO> getJokes(@QueryParam("count") int count) {
        if (count <= 0) {
            throw new BadRequestException("The count must be 10 or greater.");
        }

        try {
            // Fetch jokes synchronously
            return jokesService.fetchRandomJokes(count).stream()
                    .map(jokeEntity -> new JokeDTO(jokeEntity.getId(), jokeEntity.getQuestion(), jokeEntity.getAnswer()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch jokes.", e);
        }
    }
}
