package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_uffici")
public class Ufficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUfficio;

    @Column(nullable = false, length = 120)
    private String descrizioneUfficio;

    @Column(nullable = false, length = 120)
    private String descrizioneComando;

    @Column(nullable = false, length = 120)
    private String emailUfficio;

    @Column(nullable = false)
    private Boolean attivo;

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

	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void setDescrizioneComando(String descrizioneComando) {
		this.descrizioneComando = descrizioneComando;
	}

	public String getEmailUfficio() {
		return emailUfficio;
	}

	public void setEmailUfficio(String emailUfficio) {
		this.emailUfficio = emailUfficio;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Ufficio [idUfficio=" + idUfficio + ", descrizioneUfficio=" + descrizioneUfficio
				+ ", descrizioneComando=" + descrizioneComando + ", emailUfficio=" + emailUfficio + ", attivo=" + attivo
				+ "]";
	}

    

}