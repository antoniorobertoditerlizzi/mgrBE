package com.vigilfuoco.mgr.model;

public class JwtResponse {
	
    private Utente utente;
    
    private String token;

    public JwtResponse(Utente utente, String token) {
        this.utente = utente;
        this.token = token;
    }

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JwtResponse [utente=" + utente + ", token=" + token + "]";
	}
    
    
}
