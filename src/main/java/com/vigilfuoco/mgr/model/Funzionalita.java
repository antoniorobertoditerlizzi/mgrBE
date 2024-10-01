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
    @Column(name = "id_funzionalita")
    private Long idFunzionalita;

    @Column(name = "codice_funzionalita", nullable = false, length = 10)
    private String codiceFunzionalita;

    @Column(name = "descrizione_funzionalita", nullable = false, length = 120)
    private String descrizioneFunzionalita;

    @Column(name = "titolo", nullable = false, length = 50)
    private String titolo;

    @Column(name = "url", nullable = false, length = 250)
    private String url;

	
	public Long getIdFunzionalita() {
		return idFunzionalita;
	}
	
	public void setIdFunzionalita(Long idFunzionalita) {
		this.idFunzionalita = idFunzionalita;
	}
	
	
	public String getCodiceFunzionalita() {
		return codiceFunzionalita;
	}

	public void setCodiceFunzionalita(String codiceFunzionalita) {
		this.codiceFunzionalita = codiceFunzionalita;
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
		return "Funzionalita [idFunzionalita=" + idFunzionalita + ", codiceFunzionalita=" + codiceFunzionalita
				+ ", descrizioneFunzionalita=" + descrizioneFunzionalita + ", titolo=" + titolo + ", url=" + url + "]";
	}

	
  
}