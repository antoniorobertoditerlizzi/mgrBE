package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Indirizzo {

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false)
    private String cap;

    @Column(nullable = false)
    private String comune;

    @Id
    @Column(nullable = false)
    private String codIstat;

    @Column(nullable = false)
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
