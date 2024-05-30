package com.vigilfuoco.mgr.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetMenuByRole {
	private static final Logger logger = LogManager.getLogger(GetMenuByRole.class);

	public static JSONObject getMenuByRole(int idRuolo, Resource menuResource) throws IOException {

	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(menuResource.getInputStream()))) {
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	        String jsonString = sb.toString();
	        logger.debug(jsonString);

	        // Decodifica del JSON e ricerca del menu per RUOLO
	        JSONObject jsonObject = new JSONObject(jsonString);

	        // Controllo l'esistenza del ramo "json_menu"
	        if (jsonObject.has("json_menu")) {

	            // Converto "json_menu" in array
	            JSONArray menuArray = jsonObject.getJSONArray("json_menu");

	            // Loop nell'array per cercare il ruolo che metcha con il ruolo in input
	            for (int i = 0; i < menuArray.length(); i++) {
	                JSONObject menuEntry = menuArray.getJSONObject(i);

	                // controllo che il campo "id_ruolo" exists ed è di tipo integer
	                if (menuEntry.get("id_ruolo") instanceof Integer) {
	                    int menuRole = menuEntry.getInt("id_ruolo");
	                    if (menuRole == idRuolo) {
	                        // Accesso al menu del ruolo desiderato
	                        JSONObject menuObject = menuEntry.getJSONObject("voce_menu");
	                        return menuObject; // Restituisco l'oggetto "voce_menu" corrispondente al menu del ruolo desiderato
	                    }
	                } else {
	                	logger.error("Manca il campo int id_ruolo: " + i);
	                }
	            }
	            logger.error("Il Menu per il ruolo " + idRuolo + " non è stato trovato");
	            return null;
	        } else {
	        	logger.error("Chiave json_menu non trovata");
	            return null;
	        }
	    } catch (IOException e) {
	    	logger.error("Eccezione nel caricamento del file");
	        throw e;
	    }
	}

	  
}