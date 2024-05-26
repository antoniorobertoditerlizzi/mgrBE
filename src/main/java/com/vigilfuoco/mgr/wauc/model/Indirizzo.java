package com.vigilfuoco.mgr.wauc.model;


public class Indirizzo {

    private String indirizzo;

    private String cap;

    private String comune;

    private String codIstat;

    private String provincia;

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return "Indirizzo [indirizzo=" + indirizzo + ", cap=" + cap + ", comune=" + comune + ", codIstat=" + codIstat
				+ ", provincia=" + provincia + "]";
	}

	
}
