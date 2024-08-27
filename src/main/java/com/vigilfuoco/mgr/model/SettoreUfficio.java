package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_settori_uffici")
public class SettoreUfficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short idSettoreUfficio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_settore")
    private Settore settore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ufficio")
    private Ufficio ufficio;

    @Column(nullable = false)
    private Boolean attivo;

	public Short getIdSettoreUfficio() {
		return idSettoreUfficio;
	}

	public void setIdSettoreUfficio(Short idSettoreUfficio) {
		this.idSettoreUfficio = idSettoreUfficio;
	}

	public Settore getSettore() {
		return settore;
	}

	public void setSettore(Settore settore) {
		this.settore = settore;
	}

	public Ufficio getUfficio() {
		return ufficio;
	}

	public void setUfficio(Ufficio ufficio) {
		this.ufficio = ufficio;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "SettoreUfficio [idSettoreUfficio=" + idSettoreUfficio + ", settore=" + settore + ", ufficio=" + ufficio
				+ ", attivo=" + attivo + "]";
	}

    

}