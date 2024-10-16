package com.vigilfuoco.mgr.model;

public class RichiestaUpdateDTO {
    private String numeroRichiesta;
    private Long idStatoRichiesta;
    private Long idTipologiaRichiesta;
    private Boolean richiestaPersonale;
    private Long idPriorita;
    private Long idUtenteUfficioRuoloStatoCorrente;
    private Long idUtenteUfficioRuoloStatoIniziale;
    private Long idSettoreUfficio;
    
	public String getNumeroRichiesta() {
		return numeroRichiesta;
	}
	public void setNumeroRichiesta(String numeroRichiesta) {
		this.numeroRichiesta = numeroRichiesta;
	}
	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public Long getIdTipologiaRichiesta() {
		return idTipologiaRichiesta;
	}
	public void setIdTipologiaRichiesta(Long idTipologiaRichiesta) {
		this.idTipologiaRichiesta = idTipologiaRichiesta;
	}
	public Boolean getRichiestaPersonale() {
		return richiestaPersonale;
	}
	public void setRichiestaPersonale(Boolean richiestaPersonale) {
		this.richiestaPersonale = richiestaPersonale;
	}
	public Long getIdPriorita() {
		return idPriorita;
	}
	public void setIdPriorita(Long idPriorita) {
		this.idPriorita = idPriorita;
	}
	public Long getIdUtenteUfficioRuoloStatoCorrente() {
		return idUtenteUfficioRuoloStatoCorrente;
	}
	public void setIdUtenteUfficioRuoloStatoCorrente(Long idUtenteUfficioRuoloStatoCorrente) {
		this.idUtenteUfficioRuoloStatoCorrente = idUtenteUfficioRuoloStatoCorrente;
	}
	public Long getIdUtenteUfficioRuoloStatoIniziale() {
		return idUtenteUfficioRuoloStatoIniziale;
	}
	public void setIdUtenteUfficioRuoloStatoIniziale(Long idUtenteUfficioRuoloStatoIniziale) {
		this.idUtenteUfficioRuoloStatoIniziale = idUtenteUfficioRuoloStatoIniziale;
	}
	public Long getIdSettoreUfficio() {
		return idSettoreUfficio;
	}
	public void setIdSettoreUfficio(Long idSettoreUfficio) {
		this.idSettoreUfficio = idSettoreUfficio;
	}
	@Override
	public String toString() {
		return "RichiestaUpdateDTO [numeroRichiesta=" + numeroRichiesta + ", idStatoRichiesta=" + idStatoRichiesta
				+ ", idTipologiaRichiesta=" + idTipologiaRichiesta + ", richiestaPersonale=" + richiestaPersonale
				+ ", idPriorita=" + idPriorita + ", idUtenteUfficioRuoloStatoCorrente="
				+ idUtenteUfficioRuoloStatoCorrente + ", idUtenteUfficioRuoloStatoIniziale="
				+ idUtenteUfficioRuoloStatoIniziale + ", idSettoreUfficio=" + idSettoreUfficio + "]";
	}

    
	
}