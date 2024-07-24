package com.vigilfuoco.mgr.controller;

public class InvalidRequestStateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestStateException(String message) {
        super(message);
    }
}