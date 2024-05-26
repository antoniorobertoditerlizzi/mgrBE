package com.vigilfuoco.mgr.wauc.model;


public class Qualifica {

    private String nome;

    private Gruppo gruppo;
    
    private String codSettore;

    private String codice;

    private String descrizione;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Gruppo getGruppo() {
		return gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	public String getCodSettore() {
		return codSettore;
	}

	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}

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
		return "Qualifica [nome=" + nome + ", gruppo=" + gruppo + ", codSettore=" + codSettore + ", codice=" + codice
				+ ", descrizione=" + descrizione + "]";
	}

	

}
