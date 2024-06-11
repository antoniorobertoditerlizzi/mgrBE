package com.vigilfuoco.mgr.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_modelli_compilati")
public class ModelloCompilato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModelloCompilato;

    @Column(nullable = false)
    @Lob
    private byte[] fileModello;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modello")
    private Modello modello;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_richiesta")
    private Richiesta richiesta;

    @Column(nullable = false)
    @Lob
    private byte[] transcodificaModelloCompilato;

	public Long getIdModelloCompilato() {
		return idModelloCompilato;
	}

	public void setIdModelloCompilato(Long idModelloCompilato) {
		this.idModelloCompilato = idModelloCompilato;
	}

	public byte[] getFileModello() {
		return fileModello;
	}

	public void setFileModello(byte[] fileModello) {
		this.fileModello = fileModello;
	}

	public Modello getModello() {
		return modello;
	}

	public void setModello(Modello modello) {
		this.modello = modello;
	}

	public Richiesta getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(Richiesta richiesta) {
		this.richiesta = richiesta;
	}

	public byte[] getTranscodificaModelloCompilato() {
		return transcodificaModelloCompilato;
	}

	public void setTranscodificaModelloCompilato(byte[] transcodificaModelloCompilato) {
		this.transcodificaModelloCompilato = transcodificaModelloCompilato;
	}

	@Override
	public String toString() {
		return "ModelloCompilato [idModelloCompilato=" + idModelloCompilato + ", fileModello="
				+ Arrays.toString(fileModello) + ", modello=" + modello + ", richiesta=" + richiesta
				+ ", transcodificaModelloCompilato=" + Arrays.toString(transcodificaModelloCompilato) + "]";
	}

    

}