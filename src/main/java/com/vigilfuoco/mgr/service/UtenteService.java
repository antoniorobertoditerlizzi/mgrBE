package com.vigilfuoco.mgr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.controller.RichiestaException;
import com.vigilfuoco.mgr.model.JwtResponse;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.repository.UtenteWAUCRepository;
import com.vigilfuoco.mgr.token.JwtTokenProvider;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final Resource menuResource; 
	private static final Logger logger = LogManager.getLogger(UtenteService.class);


	@Autowired
	public UtenteService(UtenteRepository utenteRepository, @Value("classpath:menu.json") Resource menuResource) {
	    this.utenteRepository = utenteRepository;
	    this.menuResource = menuResource;
	}
	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
	@Autowired
    private UtenteWAUCRepository utenteWAUCRepository;
	
    @Autowired
    private BlacklistServiceImpl blacklistService;

	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();

	public ResponseEntity<JwtResponse> login(List<UtenteWAUC> utentiList, String accountName) throws IOException {
		Utente savedUser = new Utente();
		
		List<Utente> checkUserFound = utenteRepository.findByAccount(accountName);
		
	    if (!utentiList.isEmpty()) {
		    try {
		      // genero token di sessione
		      //JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
		      String token = jwtTokenProvider.generateToken(accountName);
		      
		      //Controllo che il token non sia nullo e che l'utente nel DB non è mai stato censito prima
		      if (token!= null) {
		    	  if(checkUserFound.isEmpty()) {
		    		  // scrivo a db l'utente trovato
		    		  savedUser = utenteWAUC_to_Utente_Service.salvaUtenteTrovato(utentiList.get(0),utenteWAUCRepository);

				      if (savedUser != null) {
				    	// restituisco al WS in output i dati salvati e genero anche il token di sessione
						try {
							//Recupero il menu utente in base al ruolo
						    int roleId = 1; //WIP savedUser.getRuoloID();
						    
						    // Recupero il menu associato al ruolo utente
						    //JSONObject menuObject = getMenuByRole(roleId, menuResource);
						    
						    // Recupero il menu associato al ruolo utente. Come oggetto dinamico.
						    Map<String, Object> menuObject = getMenuByRole_OBJ(roleId);

					    	return ResponseEntity.ok(new JwtResponse(savedUser, menuObject, token));
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Errore nella generazione del token: " + e.toString());
						}
				      }
		    	  } else {
				      logger.error("Utente già censito a DB." + checkUserFound.get(0).getAccount());
				      return ResponseEntity.ok(new JwtResponse(null, null, "Utente " + checkUserFound.get(0).getAccount() +" già censito a DB."));
		    	  }

		      }
		    } catch (IllegalArgumentException e) {
		      logger.error("Error saving user: " + e.getMessage());
		      throw new RichiestaException("Errore durante il salvataggio della richiesta: " + e.getMessage());
		    }
		}
    	// restituisco al WS in output i dati salvati
		return null;
	}
	
	
	public ResponseEntity<String> logout(String token) {
        if (blacklistService.isTokenBlacklisted(token)) {
            logger.warn("Token presente nella Blacklist. ", token);
        } else {
        	jwtTokenProvider.invalidateToken(token); 	// Invalido il token
        }
        
        return ResponseEntity.ok("Logout effettuato con successo"); 
	}
	
	
    public ResponseEntity<String> getMenuByRoleFromAccount(String accountName) throws IOException {
        // Retrieve user information
        List<Utente> utenti = utenteRepository.findByAccount(accountName);

        if (utenti.isEmpty()) {
            return new ResponseEntity<>("Account non trovato.", HttpStatus.NOT_FOUND);
        }

        Utente utente = utenti.get(0); // Prendo il primo utente trovato

        // Estraggo il ruolo utente
        int roleId = 1; //WIP utente.getRuoloID();

        // Recupero dal ruolo utente il menu corrispondente
        JSONObject menuObject = getMenuByRole(roleId, menuResource);

        if (menuObject == null) {
            return new ResponseEntity<>("Menu non trovato per il ruolo dell'utente. RoleID: " + roleId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menuObject.toString(), HttpStatus.OK);
    }


	public JSONObject getMenuByRole(int idRuolo, Resource menuResource) throws IOException {

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
	
	
	public ResponseEntity<String> getMenuByRoleWS(int idRuolo) throws IOException {
	       JSONObject strutturaMenu = null;
	        //Eventualmente si salva il json al DB e non piu su file
	        JSONObject menuJson = getMenuByRole(idRuolo, menuResource);
	        if (menuJson != null) {
	            if (!menuJson.isEmpty()) {
	                strutturaMenu = menuJson;
	            } else {
	            	logger.error("Menu vuoto");
	            }
	        } else {
	        	logger.error("Nessun Menu trovato appartenente al ruolo " + idRuolo + " richiesto");
	        }
	        
	        logger.debug(menuJson);
			return new ResponseEntity<String>(strutturaMenu.toString(), HttpStatus.OK);
	}
	
	
	
	public String getMenuByRoleWSString(int idRuolo) throws IOException {
	       String strutturaMenu = null;
	        //Eventualmente si salva il json al DB e non piu su file
	        JSONObject menuJson = getMenuByRole(idRuolo, menuResource);
	        if (menuJson != null) {
	            if (!menuJson.isEmpty()) {
	                strutturaMenu = menuJson.toString();
	            } else {
	            	logger.error("Menu vuoto");
	            }
	        } else {
	        	logger.error("Nessun Menu trovato appartenente al ruolo " + idRuolo + " richiesto");
	        }
	        
	        logger.debug(menuJson);
			return strutturaMenu;
	}
	
	@SuppressWarnings("unchecked")
	public java.util.Map<String, Object> getMenuByRole_OBJ(int idRuolo) throws IOException {
		String jsonString = getMenuByRoleWSString(idRuolo);
	    Gson gson = new Gson();
	    Map<String, Object> jsonData = gson.fromJson(jsonString, Map.class);
	return jsonData;
	}
	
}