package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class TipoPersonale {
	@Id
    @Column(nullable = false)
    private String codice;
    
    @Column(nullable = false)
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
		return "TipoPersonale [codice=" + codice + ", descrizione=" + descrizione + "]";
	}



}
