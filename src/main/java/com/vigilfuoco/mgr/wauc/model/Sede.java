package com.vigilfuoco.mgr.wauc.model;


public class Sede {

    private String id;

    private String codice;

    private String codDistaccamento;

    private String descrizione;

    private String descrizionePadre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCodDistaccamento() {
		return codDistaccamento;
	}

	public void setCodDistaccamento(String codDistaccamento) {
		this.codDistaccamento = codDistaccamento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizionePadre() {
		return descrizionePadre;
	}

	public void setDescrizionePadre(String descrizionePadre) {
		this.descrizionePadre = descrizionePadre;
	}

	@Override
	public String toString() {
		return "Sede [id=" + id + ", codice=" + codice + ", codDistaccamento=" + codDistaccamento + ", descrizione="
				+ descrizione + ", descrizionePadre=" + descrizionePadre + "]";
	}
    
    
}