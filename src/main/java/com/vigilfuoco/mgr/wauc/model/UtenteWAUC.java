package com.vigilfuoco.mgr.wauc.model;

import java.util.List;


public class UtenteWAUC {

    private String codiceFiscale;

    private String cognome;

    private String nome;

    private String sesso;
    
    private String accountDipvvf;
    
    private String emailVigilfuoco;

    private Qualifica qualifica;
    
    private List<Specializzazioni> specializzazioni;
    
    private DataNascita nascita;

    private Indirizzo residenza;

    private Indirizzo domicilio;

    private List<String> contatti;

    private Sede sede;
    
    private String turno;
    
    private String saltoTurno;
    
    private TipoPersonale tipoPersonale;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getAccountDipvvf() {
		return accountDipvvf;
	}

	public void setAccountDipvvf(String accountDipvvf) {
		this.accountDipvvf = accountDipvvf;
	}

	public String getEmailVigilfuoco() {
		return emailVigilfuoco;
	}

	public void setEmailVigilfuoco(String emailVigilfuoco) {
		this.emailVigilfuoco = emailVigilfuoco;
	}

	public Qualifica getQualifica() {
		return qualifica;
	}

	public void setQualifica(Qualifica qualifica) {
		this.qualifica = qualifica;
	}

	public List<Specializzazioni> getSpecializzazioni() {
		return specializzazioni;
	}

	public void setSpecializzazioni(List<Specializzazioni> specializzazioni) {
		this.specializzazioni = specializzazioni;
	}

	public DataNascita getNascita() {
		return nascita;
	}

	public void setNascita(DataNascita nascita) {
		this.nascita = nascita;
	}

	public Indirizzo getResidenza() {
		return residenza;
	}

	public void setResidenza(Indirizzo residenza) {
		this.residenza = residenza;
	}

	public Indirizzo getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Indirizzo domicilio) {
		this.domicilio = domicilio;
	}

	public List<String> getContatti() {
		return contatti;
	}

	public void setContatti(List<String> contatti) {
		this.contatti = contatti;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getSaltoTurno() {
		return saltoTurno;
	}

	public void setSaltoTurno(String saltoTurno) {
		this.saltoTurno = saltoTurno;
	}

	public TipoPersonale getTipoPersonale() {
		return tipoPersonale;
	}

	public void setTipoPersonale(TipoPersonale tipoPersonale) {
		this.tipoPersonale = tipoPersonale;
	}

	public UtenteWAUC() {
		super();
		this.codiceFiscale = codiceFiscale;
		this.cognome = cognome;
		this.nome = nome;
		this.sesso = sesso;
		this.accountDipvvf = accountDipvvf;
		this.emailVigilfuoco = emailVigilfuoco;
		this.qualifica = qualifica;
		this.specializzazioni = specializzazioni;
		this.nascita = nascita;
		this.residenza = residenza;
		this.domicilio = domicilio;
		this.contatti = contatti;
		this.sede = sede;
		this.turno = turno;
		this.saltoTurno = saltoTurno;
		this.tipoPersonale = tipoPersonale;
	}

	@Override
	public String toString() {
		return "UtenteWAUC [codiceFiscale=" + codiceFiscale + ", cognome=" + cognome + ", nome=" + nome + ", sesso=" + sesso
				+ ", accountDipvvf=" + accountDipvvf + ", emailVigilfuoco=" + emailVigilfuoco + ", qualifica="
				+ qualifica + ", specializzazioni=" + specializzazioni + ", nascita=" + nascita + ", residenza="
				+ residenza + ", domicilio=" + domicilio + ", contatti=" + contatti + ", sede=" + sede + ", turno="
				+ turno + ", saltoTurno=" + saltoTurno + ", tipoPersonale=" + tipoPersonale + "]";
	}


}


