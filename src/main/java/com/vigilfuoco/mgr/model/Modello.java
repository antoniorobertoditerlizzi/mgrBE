package com.vigilfuoco.mgr.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_modelli")
public class Modello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modello")
    private Long idModello;

    @Column(name = "descrizione_modello", nullable = false, length = 120)
    private String descrizioneModello;

    @Column(name = "transcodifica_modello", nullable = false)
    @Lob
    private byte[] transcodificaModello;

    @Column(name = "attivo", nullable = false)
    private boolean attivo;


    public Modello() {
    }

    public Modello(Long idModello, String descrizioneModello, byte[] transcodificaModello, boolean attivo) {
        this.idModello = idModello;
        this.descrizioneModello = descrizioneModello;
        this.transcodificaModello = transcodificaModello;
        this.attivo = attivo;
    }



	public Long getIdModello() {
		return idModello;
	}

	public void setIdModello(Long idModello) {
		this.idModello = idModello;
	}

	public String getDescrizioneModello() {
		return descrizioneModello;
	}

	public void setDescrizioneModello(String descrizioneModello) {
		this.descrizioneModello = descrizioneModello;
	}

	public byte[] getTranscodificaModello() {
		return transcodificaModello;
	}

	public void setTranscodificaModello(byte[] transcodificaModello) {
		this.transcodificaModello = transcodificaModello;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Modello [idModello=" + idModello + ", descrizioneModello=" + descrizioneModello
				+ ", transcodificaModello=" + Arrays.toString(transcodificaModello) + ", attivo=" + attivo + "]";
	}



}