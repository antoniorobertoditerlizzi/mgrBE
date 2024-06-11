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
@Table(name = "tbl_modelli_tipologie_richieste")
public class ModelloTipologiaRichiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModelloTipologiaRichiesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modello")
    private Modello modello;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipologia_richiesta")
    private TipologiaRichiesta tipologiaRichiesta;

    @Column(nullable = false)
    private Boolean attivo;

    @Column(nullable = true)
    private Date dataDisattivazione;

	public Long getIdModelloTipologiaRichiesta() {
		return idModelloTipologiaRichiesta;
	}

	public void setIdModelloTipologiaRichiesta(Long idModelloTipologiaRichiesta) {
		this.idModelloTipologiaRichiesta = idModelloTipologiaRichiesta;
	}

	public Modello getModello() {
		return modello;
	}

	public void setModello(Modello modello) {
		this.modello = modello;
	}

	public TipologiaRichiesta getTipologiaRichiesta() {
		return tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Date getDataDisattivazione() {
		return dataDisattivazione;
	}

	public void setDataDisattivazione(Date dataDisattivazione) {
		this.dataDisattivazione = dataDisattivazione;
	}

	@Override
	public String toString() {
		return "ModelloTipologiaRichiesta [idModelloTipologiaRichiesta=" + idModelloTipologiaRichiesta + ", modello="
				+ modello + ", tipologiaRichiesta=" + tipologiaRichiesta + ", attivo=" + attivo
				+ ", dataDisattivazione=" + dataDisattivazione + "]";
	}

    

}