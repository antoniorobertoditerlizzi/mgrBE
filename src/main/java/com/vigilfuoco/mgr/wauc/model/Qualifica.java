package com.vigilfuoco.mgr.wauc.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Qualifica {

    @Column(nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gruppo_id")
    private Gruppo gruppo;
    
    @Column(nullable = false)
    private String codSettore;

    @Id
    @Column(nullable = false)
    private String codice;

    @Column(nullable = false)
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
