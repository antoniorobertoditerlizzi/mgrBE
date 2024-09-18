package com.vigilfuoco.mgr.model;

import java.util.List;

public class UfficioRichieste {

    private Long idUfficio;
    private String descrizioneUfficio;
    private List<Richiesta> richieste; 
    
	public Long getIdUfficio() {
		return idUfficio;
	}
	public void setIdUfficio(Long idUfficio) {
		this.idUfficio = idUfficio;
	}
	public String getDescrizioneUfficio() {
		return descrizioneUfficio;
	}
	public void setDescrizioneUfficio(String descrizioneUfficio) {
		this.descrizioneUfficio = descrizioneUfficio;
	}
	public List<Richiesta> getRichieste() {
		return richieste;
	}
	public void setRichieste(List<Richiesta> richieste) {
		this.richieste = richieste;
	}
	@Override
	public String toString() {
		return "UfficioRichieste [idUfficio=" + idUfficio + ", descrizioneUfficio=" + descrizioneUfficio
				+ ", richieste=" + richieste + "]";
	}

    
}
