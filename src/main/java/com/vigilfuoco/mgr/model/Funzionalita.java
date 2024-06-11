package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_funzionalita")
public class Funzionalita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFunzionalita;
	
	@Column(length = 120, nullable = false)
	private String descrizioneFunzionalita;
	
	@Column(length = 50, nullable = false)
	private String titolo;
	
	@Column(length = 250, nullable = false)
	private String url;

	public Long getIdFunzionalita() {
		return idFunzionalita;
	}
	
	public void setIdFunzionalita(Long idFunzionalita) {
		this.idFunzionalita = idFunzionalita;
	}
	
	public String getDescrizioneFunzionalita() {
		return descrizioneFunzionalita;
	}
	
	public void setDescrizioneFunzionalita(String descrizioneFunzionalita) {
		this.descrizioneFunzionalita = descrizioneFunzionalita;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Funzionalita [idFunzionalita=" + idFunzionalita + ", descrizioneFunzionalita=" + descrizioneFunzionalita
				+ ", titolo=" + titolo + ", url=" + url + "]";
	}
	
	
  
}