package com.vigilfuoco.mgr.model;

import java.sql.Date;

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
@Table(name = "tbl_storico_stati_richieste")
public class StoricoStatoRichiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStoricoStatoRichiesta;

    @Column(nullable = false)
    private Date dataStatoRichiesta;

    @Column(length = 3000)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stato_richiesta")
    private StatoRichiesta statoRichiesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_richiesta")
    private Richiesta richiesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente_ufficio_ruolo")
    private UtenteUfficioRuolo utenteUfficioRuolo;

	public Long getIdStoricoStatoRichiesta() {
		return idStoricoStatoRichiesta;
	}

	public void setIdStoricoStatoRichiesta(Long idStoricoStatoRichiesta) {
		this.idStoricoStatoRichiesta = idStoricoStatoRichiesta;
	}

	public Date getDataStatoRichiesta() {
		return dataStatoRichiesta;
	}

	public void setDataStatoRichiesta(Date dataStatoRichiesta) {
		this.dataStatoRichiesta = dataStatoRichiesta;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public StatoRichiesta getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(StatoRichiesta statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public Richiesta getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(Richiesta richiesta) {
		this.richiesta = richiesta;
	}

	public UtenteUfficioRuolo getUtenteUfficioRuolo() {
		return utenteUfficioRuolo;
	}

	public void setUtenteUfficioRuolo(UtenteUfficioRuolo utenteUfficioRuolo) {
		this.utenteUfficioRuolo = utenteUfficioRuolo;
	}

	@Override
	public String toString() {
		return "StoricoStatoRichiesta [idStoricoStatoRichiesta=" + idStoricoStatoRichiesta + ", dataStatoRichiesta="
				+ dataStatoRichiesta + ", note=" + note + ", statoRichiesta=" + statoRichiesta + ", richiesta="
				+ richiesta + ", utenteUfficioRuolo=" + utenteUfficioRuolo + "]";
	}

    

}