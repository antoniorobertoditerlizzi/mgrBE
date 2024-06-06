package com.vigilfuoco.mgr.model;

import java.util.Map;

public class JwtResponse {
	
    private Utente utente;
    
	private Map<String, Object> menuObject;
    
    private String token;
    
    private String note;


    public JwtResponse(Utente utente, Map<String, Object> menuObject, String token, String note) {
        this.utente = utente;
        this.menuObject = menuObject;
        this.token = token;
        this.note = note;
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


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public String toString() {
		return "JwtResponse [utente=" + utente + ", menuObject=" + menuObject + ", token=" + token + ", note=" + note
				+ "]";
	}

    
}
