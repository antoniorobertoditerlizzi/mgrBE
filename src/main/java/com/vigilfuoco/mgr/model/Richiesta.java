package com.vigilfuoco.mgr.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_richieste")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRichiesta;

    @Column(nullable = false, length = 14)
    private String numeroRichiesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_stato_richiesta")
    private StatoRichiesta statoRichiesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipologia_richiesta")
    private TipologiaRichiesta tipologiaRichiesta;

    @Column(nullable = false)
    private Boolean richiestaPersonale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priorita")
    private Priorita priorita;

    @Column(nullable = false)
    private LocalDateTime  dataInserimentoRichiesta;

    @Column(nullable = false)
    private LocalDateTime  dataUltimoStatoRichiesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente_ufficio_ruolo_stato_corrente")
    private UtenteUfficioRuolo utenteUfficioRuoloStatoCorrente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente_ufficio_ruolo_stato_iniziale")
    private UtenteUfficioRuolo utenteUfficioRuoloStatoIniziale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_settore_ufficio")
    private SettoreUfficio settoreUfficio; //SettoreUfficioEvasione cambiare in settore ufficio

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getNumeroRichiesta() {
		return numeroRichiesta;
	}

	public void setNumeroRichiesta(String numeroRichiesta) {
		this.numeroRichiesta = numeroRichiesta;
	}

	public StatoRichiesta getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(StatoRichiesta statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public TipologiaRichiesta getTipologiaRichiesta() {
		return tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	public Boolean getRichiestaPersonale() {
		return richiestaPersonale;
	}

	public void setRichiestaPersonale(Boolean richiestaPersonale) {
		this.richiestaPersonale = richiestaPersonale;
	}

	public Priorita getPriorita() {
		return priorita;
	}

	public void setPriorita(Priorita priorita) {
		this.priorita = priorita;
	}

	public LocalDateTime  getDataInserimentoRichiesta() {
		return dataInserimentoRichiesta;
	}

	public void setDataInserimentoRichiesta(LocalDateTime  dataInserimentoRichiesta) {
		this.dataInserimentoRichiesta = dataInserimentoRichiesta;
	}

	public LocalDateTime  getDataUltimoStatoRichiesta() {
		return dataUltimoStatoRichiesta;
	}

	public void setDataUltimoStatoRichiesta(LocalDateTime  dataUltimoStatoRichiesta) {
		this.dataUltimoStatoRichiesta = dataUltimoStatoRichiesta;
	}

	public UtenteUfficioRuolo getUtenteUfficioRuoloStatoCorrente() {
		return utenteUfficioRuoloStatoCorrente;
	}

	public void setUtenteUfficioRuoloStatoCorrente(UtenteUfficioRuolo utenteUfficioRuoloStatoCorrente) {
		this.utenteUfficioRuoloStatoCorrente = utenteUfficioRuoloStatoCorrente;
	}

	public UtenteUfficioRuolo getUtenteUfficioRuoloStatoIniziale() {
		return utenteUfficioRuoloStatoIniziale;
	}

	public void setUtenteUfficioRuoloStatoIniziale(UtenteUfficioRuolo utenteUfficioRuoloStatoIniziale) {
		this.utenteUfficioRuoloStatoIniziale = utenteUfficioRuoloStatoIniziale;
	}

	public SettoreUfficio getSettoreUfficio() {
		return settoreUfficio;
	}

	public void setSettoreUfficio(SettoreUfficio settoreUfficio) {
		this.settoreUfficio = settoreUfficio;
	}

	@Override
	public String toString() {
		return "Richiesta [idRichiesta=" + idRichiesta + ", numeroRichiesta=" + numeroRichiesta + ", statoRichiesta="
				+ statoRichiesta + ", tipologiaRichiesta=" + tipologiaRichiesta + ", richiestaPersonale="
				+ richiestaPersonale + ", priorita=" + priorita + ", dataInserimentoRichiesta="
				+ dataInserimentoRichiesta + ", dataUltimoStatoRichiesta=" + dataUltimoStatoRichiesta
				+ ", utenteUfficioRuoloStatoCorrente=" + utenteUfficioRuoloStatoCorrente
				+ ", utenteUfficioRuoloStatoIniziale=" + utenteUfficioRuoloStatoIniziale + ", settoreUfficio="
				+ settoreUfficio + "]";
	}

    
}