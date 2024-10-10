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
    @Column(name = "id_stato_richiesta", nullable = false, updatable = false)
    private Long idStatoRichiesta;

    @Column(name = "descrizione_stato_richiesta", length = 120, nullable = false)
    private String descrizioneStatoRichiesta;

    @Column(name = "attivo", nullable = false)
    private boolean attivo;

    @Column(name = "descrizione_stato", length = 120, nullable = false)
    private String descrizioneStato;

    @Column(name = "percentuale", nullable = false)
    private int percentuale;

    @Column(name = "colore", length = 10, nullable = false)
    private String colore;
    
    @Column(name = "tab_wizard", nullable = false)
    private int tabWizard;

    // Getters and Setters
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

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public String getDescrizioneStato() {
        return descrizioneStato;
    }

    public void setDescrizioneStato(String descrizioneStato) {
        this.descrizioneStato = descrizioneStato;
    }

    public int getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(int percentuale) {
        this.percentuale = percentuale;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    
	public int getTabWizard() {
		return tabWizard;
	}

	public void setTabWizard(int tabWizard) {
		this.tabWizard = tabWizard;
	}

	@Override
	public String toString() {
		return "StatoRichiesta [idStatoRichiesta=" + idStatoRichiesta + ", descrizioneStatoRichiesta="
				+ descrizioneStatoRichiesta + ", attivo=" + attivo + ", descrizioneStato=" + descrizioneStato
				+ ", percentuale=" + percentuale + ", colore=" + colore + ", tabWizard=" + tabWizard + "]";
	}


    
    
    
}
