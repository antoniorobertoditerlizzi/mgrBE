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
@Table(name = "tbl_ruoli_funzionalita")
public class RuoloFunzionalita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRuoloFunzionalita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funzionalita")
    private Funzionalita funzionalita;

    @Column(nullable = false)
    private Boolean attivo;

	public Long getIdRuoloFunzionalita() {
		return idRuoloFunzionalita;
	}

	public void setIdRuoloFunzionalita(Long idRuoloFunzionalita) {
		this.idRuoloFunzionalita = idRuoloFunzionalita;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Funzionalita getFunzionalita() {
		return funzionalita;
	}

	public void setFunzionalita(Funzionalita funzionalita) {
		this.funzionalita = funzionalita;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "RuoloFunzionalita [idRuoloFunzionalita=" + idRuoloFunzionalita + ", ruolo=" + ruolo + ", funzionalita="
				+ funzionalita + ", attivo=" + attivo + "]";
	}

    

}