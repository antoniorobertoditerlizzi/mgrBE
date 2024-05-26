package com.vigilfuoco.mgr.wauc.model;


public class Gruppo {

    private String codice;
    
    private String descrizione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Gruppo [codice=" + codice + ", descrizione=" + descrizione + "]";
	}


}
