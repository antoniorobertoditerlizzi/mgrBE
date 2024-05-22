package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class DataNascita {

    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private String comune;

    @Id
    @Column(nullable = false)
    private String codIstat;

    @Column(nullable = false)
    private String provincia;


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
		return "DataNascita [data=" + data + ", comune=" + comune + ", codIstat=" + codIstat + ", provincia="
				+ provincia + "]";
	}

}
