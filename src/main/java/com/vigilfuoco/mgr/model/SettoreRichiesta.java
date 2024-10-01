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
@Table(name = "tbl_settori_richieste")
public class SettoreRichiesta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_settore_richiesta")
    private Long idSettoreRichiesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_settore", nullable = false)
    private Settore settore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipologia_richiesta", nullable = false)
    private TipologiaRichiesta tipologiaRichiesta;

    @Column(name = "attivo", nullable = false)
    private Boolean attivo;

	public Long getIdSettoreRichiesta() {
		return idSettoreRichiesta;
	}

	public void setIdSettoreRichiesta(Long idSettoreRichiesta) {
		this.idSettoreRichiesta = idSettoreRichiesta;
	}

	public TipologiaRichiesta getTipologiaRichiesta() {
		return tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	public Settore getSettore() {
		return settore;
	}

	public void setSettore(Settore settore) {
		this.settore = settore;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "SettoreRichiesta [idSettoreRichiesta=" + idSettoreRichiesta + ", tipologiaRichiesta="
				+ tipologiaRichiesta + ", settore=" + settore + ", attivo=" + attivo + "]";
	}

    

}