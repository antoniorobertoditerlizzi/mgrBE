package com.vigilfuoco.mgr.exception;

public class InvalidRequestStateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestStateException(String message) {
        super(message);
    }
}