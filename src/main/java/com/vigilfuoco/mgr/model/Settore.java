package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_settori")
public class Settore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSettore;

    @Column(nullable = false, length = 120)
    private String descrizioneSettore;

    @Column(length = 50)
    private String emailSettore;

    @Column(nullable = false)
    private Boolean attivo;

	public Long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public String getDescrizioneSettore() {
		return descrizioneSettore;
	}

	public void setDescrizioneSettore(String descrizioneSettore) {
		this.descrizioneSettore = descrizioneSettore;
	}

	public String getEmailSettore() {
		return emailSettore;
	}

	public void setEmailSettore(String emailSettore) {
		this.emailSettore = emailSettore;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Settore [idSettore=" + idSettore + ", descrizioneSettore=" + descrizioneSettore + ", emailSettore="
				+ emailSettore + ", attivo=" + attivo + "]";
	}

    

}