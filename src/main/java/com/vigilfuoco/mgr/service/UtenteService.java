package com.vigilfuoco.mgr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
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

	@SuppressWarnings("null")
	public ResponseEntity<JwtResponse> login(String accountName, String url) throws IOException {
		Utente savedUser = new Utente();
		
		Utente checkUserFound = utenteRepository.findByAccount(accountName);

		
	    try {
			// Genero token di sessione
			String token = jwtTokenProvider.generateToken(accountName);
			
			// Recupero il menu utente in base al ruolo
			int roleId = 1; //WIP savedUser.getRuoloID();
			
			// Recupero il menu associato al ruolo utente. Come oggetto dinamico.
			List<Object> menu = getMenuByRole_OBJ(roleId);
		    
			// Controllo che il token non sia nullo e che l'utente nel DB non è mai stato censito prima
			if (token!= null) {
		    	  if(checkUserFound == null) {
		    			
		    		    // Recupero i dati dell'utente da WAUC da salvare nella tabella Utente MGR
		    			List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
		    			if (!utentiList.isEmpty()) {
			
								// Scrivo a db l'utente trovato
								savedUser = utenteWAUC_to_Utente_Service.salvaUtenteTrovato(utentiList.get(0),utenteWAUCRepository);
		
								if (savedUser != null) {
									// Restituisco al WS in output i dati salvati e genero anche il token di sessione
								try {
								    logger.info("Procedura di Importazione Nuovo Utente " + savedUser.getAccount() +" ultimata con successo. Utente correttamente importato nel DB MGR.");
									return ResponseEntity.ok(new JwtResponse(savedUser, menu, token, "Procedura di Importazione Nuovo Utente " + savedUser.getAccount() +" ultimata con successo. Utente correttamente importato nel DB MGR."));
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("Errore nella generazione del token: " + e.toString());
								}
						}
				      }
		    	  } else {
				      logger.info("Utente " + checkUserFound.getAccount() + " già censito a DB.");
				      return ResponseEntity.ok(new JwtResponse(checkUserFound, menu, token, "Utente " + checkUserFound.getAccount() +" già censito a DB."));
		    	  }
		      }
	    } catch (IllegalArgumentException e) {
	      logger.error("Error saving user: " + e.getMessage());
	      throw new RichiestaException("Errore durante il salvataggio della richiesta: " + e.getMessage());
	    }
	    
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
        Utente utenti = utenteRepository.findByAccount(accountName);

        if (utenti != null) {
            return new ResponseEntity<>("Account non trovato.", HttpStatus.NOT_FOUND);
        }

        // Estraggo il ruolo utente
        int roleId = 1; //WIP utente.getRuoloID();

        // Recupero dal ruolo utente il menu corrispondente
         JSONArray menuObject = getMenuByRole(roleId, menuResource);

        if (menuObject == null) {
            return new ResponseEntity<>("Menu non trovato per il ruolo dell'utente. RoleID: " + roleId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menuObject.toString(), HttpStatus.OK);
    }


    public static JSONArray getMenuByRole(int idRuolo, Resource menuResource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(menuResource.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String jsonString = sb.toString();
            logger.debug(jsonString);

            // Decodifica del json
            JSONObject jsonObject = new JSONObject(jsonString);

            // Check per chiave "json_menu"
            if (jsonObject.has("json_menu")) {

                JSONArray menuArray = jsonObject.getJSONArray("json_menu");

                // Cerco nel menu il menu corrispondente al ruolo in input
                for (int i = 0; i < menuArray.length(); i++) {
                    JSONObject menuEntry = menuArray.getJSONObject(i);

                    if (menuEntry.has("id_ruolo") && menuEntry.get("id_ruolo") instanceof Integer) {
                        int menuRole = menuEntry.getInt("id_ruolo");

                        if (menuRole == idRuolo) {
                            try {
                                // Accedo al menu in base al ruolo
                                Object menuObject = menuEntry.get("voci_menu");
                                if (menuObject instanceof JSONArray) {
                                    return (JSONArray) menuObject;
                                } else {
                                    logger.error("Errore sul tipo di 'voci_menu' per il ruolo " + idRuolo);
                                    return null;
                                }
                            } catch (JSONException e) {
                                // Handle potential error if "voci_menu" is invalid
                                logger.error("Invalido campo 'voci_menu' per il ruolo " + idRuolo, e);
                                return null;
                            }
                        }
                    } else {
                        logger.error("Campo id_ruolo mancante o invalido " + i);
                    }
                }

                logger.error("Menu per il ruolo " + idRuolo + " non trovato");
                return null;
            } else {
                logger.error("'json_menu chiave non trovata nel JSON");
                return null;
            }
        } catch (IOException e) {
            logger.error("Errore di lettura del file ", e);
            throw e;
        }
    }

	
	public ResponseEntity<String> getMenuByRoleWS(int idRuolo) throws IOException {
	       JSONArray strutturaMenu = null;
	        //Eventualmente si salva il json al DB e non piu su file
	         JSONArray menuJson = getMenuByRole(idRuolo, menuResource);
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
	         JSONArray menuJson = getMenuByRole(idRuolo, menuResource);
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
	public List<Object> getMenuByRole_OBJ(int idRuolo) throws IOException {
		String jsonString = getMenuByRoleWSString(idRuolo);
	
	    ObjectMapper mapper = new ObjectMapper();
	    List<Object> menuList = mapper.readValue(jsonString, List.class);
	    //Map<String, Object>W jsonData = new HashMap<>();
	    //jsonData.put("array", menuList);
	return menuList;
	}
	
}