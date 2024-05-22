package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Specializzazioni {

	@Id
    @Column(nullable = false)
    private String codice;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private String dataInizioValidita;

    @Column(nullable = false)
    private String dataFineValidita;

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

	public String getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	@Override
	public String toString() {
		return "Specializzazioni [codice=" + codice + ", descrizione=" + descrizione + ", dataInizioValidita="
				+ dataInizioValidita + ", dataFineValidita=" + dataFineValidita + "]";
	}

	


}
