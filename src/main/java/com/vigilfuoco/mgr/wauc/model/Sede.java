package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sede {

    @Id
    private String id;

    @Column(nullable = false)
    private String codice;

    @Column(nullable = false)
    private String codDistaccamento;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
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