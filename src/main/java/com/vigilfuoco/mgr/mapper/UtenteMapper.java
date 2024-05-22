package com.vigilfuoco.mgr.mapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigilfuoco.mgr.wauc.model.Utente;

public class UtenteMapper {

    private final ObjectMapper objectMapper;

    public UtenteMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Utente> mapJsonToPersonaList(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, new TypeReference<List<Utente>>() {});
    }

    public Utente mapJsonToPersona(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, Utente.class);
    }
}