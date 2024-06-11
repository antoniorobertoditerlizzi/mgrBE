package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_priorita")
public class Priorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short idPriorita;

    @Column(nullable = false, length = 120)
    private String descrizionePriorita;

	public Short getIdPriorita() {
		return idPriorita;
	}

	public void setIdPriorita(Short idPriorita) {
		this.idPriorita = idPriorita;
	}

	public String getDescrizionePriorita() {
		return descrizionePriorita;
	}

	public void setDescrizionePriorita(String descrizionePriorita) {
		this.descrizionePriorita = descrizionePriorita;
	}

	@Override
	public String toString() {
		return "Priorita [idPriorita=" + idPriorita + ", descrizionePriorita=" + descrizionePriorita + "]";
	}

    

}