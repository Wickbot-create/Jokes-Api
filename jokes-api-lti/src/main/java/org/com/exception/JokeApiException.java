package org.com.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class JokeApiException extends WebApplicationException {
	public JokeApiException(String message) {
		super(message, Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build());
	}
}
