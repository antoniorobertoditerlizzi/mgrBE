package com.vigilfuoco.mgr.model;

import java.util.Map;

public class ModelloConJson  {

    private Long idModello;

    private String descrizioneModello;

    private boolean attivo;
    
    private final Map<String, Object> modelloJson;

    public ModelloConJson(Long idModello, String descrizione, Map<String, Object> modelloJson) {
        super();
        this.modelloJson = modelloJson;
    }

    public Map<String, Object> getModelloJson() {
        return modelloJson;
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

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
    
    
    
}