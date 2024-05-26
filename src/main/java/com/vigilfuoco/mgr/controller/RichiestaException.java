package com.vigilfuoco.mgr.controller;

public class RichiestaException extends RuntimeException {

    /*
	 * Gestione CUSTOM delle eccezioni
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	public RichiestaException(String message) {
        super(message);
    }
}