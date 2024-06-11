package com.vigilfuoco.mgr.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_flussi_richieste")
public class FlussoRichiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFlussoRichiesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stato_richiesta_attuale")
    private StatoRichiesta statoRichiestaAttuale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stato_richiesta_successivo")
    private StatoRichiesta statoRichiestaSuccessivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipologia_richiesta")
    private TipologiaRichiesta tipologiaRichiesta;

	public Long getIdFlussoRichiesta() {
		return idFlussoRichiesta;
	}

	public void setIdFlussoRichiesta(Long idFlussoRichiesta) {
		this.idFlussoRichiesta = idFlussoRichiesta;
	}

	public StatoRichiesta getStatoRichiestaAttuale() {
		return statoRichiestaAttuale;
	}

	public void setStatoRichiestaAttuale(StatoRichiesta statoRichiestaAttuale) {
		this.statoRichiestaAttuale = statoRichiestaAttuale;
	}

	public StatoRichiesta getStatoRichiestaSuccessivo() {
		return statoRichiestaSuccessivo;
	}

	public void setStatoRichiestaSuccessivo(StatoRichiesta statoRichiestaSuccessivo) {
		this.statoRichiestaSuccessivo = statoRichiestaSuccessivo;
	}

	public TipologiaRichiesta getTipologiaRichiesta() {
		return tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	@Override
	public String toString() {
		return "FlussoRichiesta [idFlussoRichiesta=" + idFlussoRichiesta + ", statoRichiestaAttuale="
				+ statoRichiestaAttuale + ", statoRichiestaSuccessivo=" + statoRichiestaSuccessivo
				+ ", tipologiaRichiesta=" + tipologiaRichiesta + "]";
	}

    

}