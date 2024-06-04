package com.vigilfuoco.mgr.model;

public class JwtResponse {
	
    private Utente utente;
    
	private String menuObject;
    
    private String token;


    public JwtResponse(Utente utente, String menuObject, String token) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMenuObject() {
		return menuObject;
	}

	public void setMenuObject(String menuObject) {
		this.menuObject = menuObject;
	}

	@Override
	public String toString() {
		return "JwtResponse [utente=" + utente + ", menuObject=" + menuObject + ", token=" + token + "]";
	}

    
}
