package com.vigilfuoco.mgr.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_utenti_uffici_ruoli")
public class UtenteUfficioRuolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtenteUfficioRuolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_settore_ufficio")
    private SettoreUfficio settoreUfficio;

    @Column(nullable = false)
    private Boolean attivo;

    @Column(nullable = false)
    private Date dataCreazione;

    @Column(nullable = false)
    private Date dataDisattivazione;

	public Long getIdUtenteUfficioRuolo() {
		return idUtenteUfficioRuolo;
	}

	public void setIdUtenteUfficioRuolo(Long idUtenteUfficioRuolo) {
		this.idUtenteUfficioRuolo = idUtenteUfficioRuolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public SettoreUfficio getSettoreUfficio() {
		return settoreUfficio;
	}

	public void setSettoreUfficio(SettoreUfficio settoreUfficio) {
		this.settoreUfficio = settoreUfficio;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataDisattivazione() {
		return dataDisattivazione;
	}

	public void setDataDisattivazione(Date dataDisattivazione) {
		this.dataDisattivazione = dataDisattivazione;
	}

	@Override
	public String toString() {
		return "UtenteUfficioRuolo [idUtenteUfficioRuolo=" + idUtenteUfficioRuolo + ", utente=" + utente + ", ruolo="
				+ ruolo + ", settoreUfficio=" + settoreUfficio + ", attivo=" + attivo + ", dataCreazione="
				+ dataCreazione + ", dataDisattivazione=" + dataDisattivazione + "]";
	}

    

}