package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtente;

    @Column(name = "account", nullable = false, length = 50)
    private String account;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 120)
    private String cognome;

    @Column(name = "CF", nullable = false, length = 16)
    private String cf;

    @Column(name = "classificazione_utente_app", nullable = false)
    private int classificazioneUtenteApp;

    @Column(name = "email_utente", nullable = false, length = 50)
    private String emailUtente;

    @Column(name = "attivo", nullable = false)
    private boolean attivo;

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public int getClassificazioneUtenteApp() {
		return classificazioneUtenteApp;
	}

	public void setClassificazioneUtenteApp(int classificazioneUtenteApp) {
		this.classificazioneUtenteApp = classificazioneUtenteApp;
	}

	public String getEmailUtente() {
		return emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public Utente() {
		super();
		this.idUtente = idUtente;
		this.account = account;
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.classificazioneUtenteApp = classificazioneUtenteApp;
		this.emailUtente = emailUtente;
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Utente [idUtente=" + idUtente + ", account=" + account + ", nome=" + nome + ", cognome=" + cognome
				+ ", cf=" + cf + ", classificazioneUtenteApp=" + classificazioneUtenteApp + ", emailUtente="
				+ emailUtente + ", attivo=" + attivo + "]";
	}



}


