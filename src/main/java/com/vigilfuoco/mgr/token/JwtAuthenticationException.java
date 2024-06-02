package com.vigilfuoco.mgr.token;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -7257943875530445425L;
	
	private final HttpStatus statusCode;

    public JwtAuthenticationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}