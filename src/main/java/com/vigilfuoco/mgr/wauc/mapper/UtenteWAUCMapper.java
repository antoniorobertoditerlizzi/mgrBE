package com.vigilfuoco.mgr.wauc.mapper;

import java.util.List;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

/* 
 * Mapper che mappa il risultato della chiamata alle API di Utente comune in oggetto Java (Entity) Utente WAUC
 * 
 */

public class UtenteWAUCMapper {

    private final ObjectMapper objectMapper;

    public UtenteWAUCMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public List<UtenteWAUC> mapJsonToPersonaList(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, new TypeReference<List<UtenteWAUC>>() {});
    }

    public UtenteWAUC mapJsonToPersona(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, UtenteWAUC.class);
    }
    
    
}