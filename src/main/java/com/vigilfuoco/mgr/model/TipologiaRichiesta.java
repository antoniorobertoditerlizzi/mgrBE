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
@Table(name = "tbl_tipologie_richieste")
public class TipologiaRichiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short idTipologiaRichiesta;

    @Column(nullable = false, length = 120)
    private String descrizioneTipologiaRichiesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_stato_richiesta_partenza")
    private StatoRichiesta statoRichiestaPartenza;

    @Column(nullable = false)
    private Boolean attivo;

	public Short getIdTipologiaRichiesta() {
		return idTipologiaRichiesta;
	}

	public void setIdTipologiaRichiesta(Short idTipologiaRichiesta) {
		this.idTipologiaRichiesta = idTipologiaRichiesta;
	}

	public String getDescrizioneTipologiaRichiesta() {
		return descrizioneTipologiaRichiesta;
	}

	public void setDescrizioneTipologiaRichiesta(String descrizioneTipologiaRichiesta) {
		this.descrizioneTipologiaRichiesta = descrizioneTipologiaRichiesta;
	}

	public StatoRichiesta getStatoRichiestaPartenza() {
		return statoRichiestaPartenza;
	}

	public void setStatoRichiestaPartenza(StatoRichiesta statoRichiestaPartenza) {
		this.statoRichiestaPartenza = statoRichiestaPartenza;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "TipologiaRichiesta [idTipologiaRichiesta=" + idTipologiaRichiesta + ", descrizioneTipologiaRichiesta="
				+ descrizioneTipologiaRichiesta + ", statoRichiestaPartenza=" + statoRichiestaPartenza + ", attivo="
				+ attivo + "]";
	}


	/*@Override
	public String toString() {
		return "TipologiaRichiesta [idTipologiaRichiesta=" + idTipologiaRichiesta + ", descrizioneTipologiaRichiesta="
				+ descrizioneTipologiaRichiesta + ", statoRichiestaPartenza=" + statoRichiestaPartenza + ", attivo="
				+ attivo + "]";
	}*/
    

}
