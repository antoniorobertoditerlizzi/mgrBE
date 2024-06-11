package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_stati_richieste")
public class StatoRichiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatoRichiesta;

    @Column(nullable = false, length = 120)
    private String descrizioneStatoRichiesta;

    @Column(nullable = false)
    private Boolean attivo;

	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}

	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}

	public String getDescrizioneStatoRichiesta() {
		return descrizioneStatoRichiesta;
	}

	public void setDescrizioneStatoRichiesta(String descrizioneStatoRichiesta) {
		this.descrizioneStatoRichiesta = descrizioneStatoRichiesta;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "StatoRichiesta [idStatoRichiesta=" + idStatoRichiesta + ", descrizioneStatoRichiesta="
				+ descrizioneStatoRichiesta + ", attivo=" + attivo + "]";
	}

   
	
}