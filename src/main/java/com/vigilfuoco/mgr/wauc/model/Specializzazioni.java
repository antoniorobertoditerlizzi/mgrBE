package com.vigilfuoco.mgr.wauc.model;

public class Specializzazioni {

    private String codice;

    private String descrizione;

    private String dataInizioValidita;

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
