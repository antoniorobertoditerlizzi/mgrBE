package com.vigilfuoco.mgr.model;

import java.util.List;

public class JwtResponse {
	
    private Utente utente;
    
	private List<Object> menu;
    
    private String token;
    
    private String note;


    public JwtResponse(Utente utente, List<Object> menu, String token, String note) {
        this.utente = utente;
        this.menu = menu;
        this.token = token;
        this.note = note;
    }


	public Utente getUtente() {
		return utente;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}


	public List<Object> getMenu() {
		return menu;
	}


	public void setMenu(List<Object> menu) {
		this.menu = menu;
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
		return "JwtResponse [utente=" + utente + ", menu=" + menu + ", token=" + token + ", note=" + note + "]";
	}


    
}
