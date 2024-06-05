package com.vigilfuoco.mgr.model;

import java.util.Map;

public class JwtResponse {
	
    private Utente utente;
    
	private Map<String, Object> menuObject;
    
    private String token;


    public JwtResponse(Utente utente, Map<String, Object> menuObject, String token) {
        this.utente = utente;
        this.menuObject = menuObject;
        this.token = token;
    }


	public Utente getUtente() {
		return utente;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}


	public Map<String, Object> getMenuObject() {
		return menuObject;
	}


	public void setMenuObject(Map<String, Object> menuObject) {
		this.menuObject = menuObject;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "JwtResponse [utente=" + utente + ", menuObject=" + menuObject + ", token=" + token + "]";
	}



    
}
