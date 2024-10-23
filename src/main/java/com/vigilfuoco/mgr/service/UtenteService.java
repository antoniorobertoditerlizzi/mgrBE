package com.vigilfuoco.mgr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.exception.InvalidTokenException;
import com.vigilfuoco.mgr.exception.MenuException;
import com.vigilfuoco.mgr.exception.RichiestaException;
import com.vigilfuoco.mgr.model.Funzionalita;
import com.vigilfuoco.mgr.model.JwtResponse;
import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.Ufficio;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.RuoloFunzionalitaRepository;
import com.vigilfuoco.mgr.repository.RuoloRepository;
import com.vigilfuoco.mgr.repository.UfficioRepository;
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
    private UfficioRepository ufficioRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
	@Autowired
    private UtenteWAUCRepository utenteWAUCRepository;
	
    @Autowired
    private RuoloRepository ruoloRepository;

	
    @Autowired
    private BlacklistServiceImpl blacklistService;
    

    @Autowired
    private RuoloFunzionalitaRepository ruoloFunzionalitaRepository;
    
    
	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();


	public ResponseEntity<JwtResponse> login(String accountName, String url) throws IOException {
		Utente savedUser = new Utente();
		
		Utente checkUserFound = utenteRepository.findByAccount(accountName);

		
	    try {
	        // Genera il token di accesso e il refresh token
	        String accessToken = jwtTokenProvider.generateToken(accountName);
	        String refreshToken = jwtTokenProvider.generateRefreshToken(accountName);
			
			// Recupero il menu utente in base al ruolo
			int roleId = 1; //WIP savedUser.getRuoloID();
			
			// Recupero il menu associato al ruolo utente. Come oggetto dinamico.
			List<Object> menu = getMenuByRole_OBJ(roleId);
		    
			// Controllo che il token non sia nullo e che l'utente nel DB non è mai stato censito prima
			if (accessToken!= null) {
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
									return ResponseEntity.ok(new JwtResponse(savedUser, menu, accessToken, refreshToken, "Procedura di Importazione Nuovo Utente " + savedUser.getAccount() +" ultimata con successo. Utente correttamente importato nel DB MGR."));
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("Errore nella generazione del token: " + e.toString());
								}
						}
				      }
		    	  } else {
				      logger.info("Utente " + checkUserFound.getAccount() + " già censito a DB.");
				      return ResponseEntity.ok(new JwtResponse(checkUserFound, menu, accessToken, refreshToken, "Utente " + checkUserFound.getAccount() +" già censito a DB."));
		    	  }
		      }
	    } catch (IllegalArgumentException e) {
	      logger.error("Error saving user: " + e.getMessage());
	      throw new RichiestaException("Errore durante il salvataggio della richiesta: " + e.getMessage());
	    }
	    
		return null;
	}
	
    // Metodo per gestire il refresh token e restituire un nuovo access token
    public ResponseEntity<JwtResponse> refreshToken(String refreshToken) throws InvalidTokenException, IOException {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Refresh token non valido");
        }

        // Ottieni l'username dal refresh token
        String accountName = jwtTokenProvider.getUsernameFromToken(refreshToken);

        // Genera un nuovo access token che sostituirà il vecchio ottenuto nella login e poi scaduto 
        String newAccessToken = jwtTokenProvider.generateToken(accountName);

        // Opzionale: possibilita di generare un nuovo refresh token
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(accountName);

        // Restituisci i nuovi token e nelle successive invocazioni delle API dell'applicativo usare il "newAccessToken"
        return ResponseEntity.ok(new JwtResponse(null, null, newAccessToken, newRefreshToken, "Token rinnovato con successo per l'utente " + accountName));
    }
    
	public ResponseEntity<String> logout(String token) {
        if (blacklistService.isTokenBlacklisted(token)) {
            logger.warn("Token presente nella Blacklist. ", token);
        } else {
        	jwtTokenProvider.invalidateToken(token); 	// Invalido il token
        }
        
        return ResponseEntity.ok("Logout effettuato con successo"); 
	}
	
	// GENERAZIONE MENU UTENTE LOGGATO PER ACCOUNTNAME
    public List<Object> getMenuByRoleFromAccount(String accountName) throws IOException {
        // Retrieve user information
        Utente utenti = utenteRepository.findByAccount(accountName);

        if (utenti == null) {
            //return new ResponseEntity<>("Account non trovato.", HttpStatus.NOT_FOUND);
        	throw new MenuException("Account non trovato.");
        }

        // Estraggo il ruolo utente
        int roleId = 1; //WIP utente.getRuoloID();

        // Recupero dal ruolo utente il menu corrispondente
         JSONArray menuObject = getMenuByRole(roleId, menuResource);

        if (menuObject == null) {
        	throw new MenuException("Menu non trovato per il ruolo dell'utente. RoleID: " + roleId);
            //return new ResponseEntity<>("Menu non trovato per il ruolo dell'utente. RoleID: " + roleId, HttpStatus.NOT_FOUND);
        }

	    ObjectMapper mapper = new ObjectMapper();
	    @SuppressWarnings("unchecked")
		List<Object> menuList = mapper.readValue(menuObject.toString(), List.class);
	    return menuList;
        //return new ResponseEntity<>(menuObject.toString(), HttpStatus.OK);
    }

    // GENERAZIONE MENU UTENTE LOGGATO PER ID RUOLO
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
    
    // GENERAZIONE MENU PER UTENTE NON LOGGATO E PER SMALL MENU USER LOGGED
    public static JSONArray getSecondaryMenus(String menuType, Resource menuResource) throws IOException {
	  	  try (BufferedReader reader = new BufferedReader(new InputStreamReader(menuResource.getInputStream()))) {
	  	    StringBuilder sb = new StringBuilder();
	  	    String line;
	  	    while ((line = reader.readLine()) != null) {
	  	      sb.append(line).append("\n");
	  	    }
	  	    String jsonString = sb.toString();
	
	  	    // Decodifica del json
	  	    JSONObject jsonObject = new JSONObject(jsonString);
	  	   
	  	    // Check for chiave "not_logged_user_menu" (adjusted key name)
	  	    if (jsonObject.has(menuType)) {
	
	  	      JSONArray menuArray = jsonObject.getJSONArray(menuType);
	
	  	      // Return the entire "not_logged_user_menu" array (adjusted logic)
	  	      return menuArray;
	
	  	    } else {
	  	      logger.error("JSON utente non loggato, non trovato.");
	  	      return null;
	  	    }
	  	  } catch (IOException e) {
	  	    logger.error("Error reading file", e);
	  	    throw e;
	  	  } catch (JSONException e) {
	  	    logger.error("Error parsing JSON", e);
	  	    return null;
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
	
	
	
	public String getMenuByRoleWSString(String menuType, int idRuolo) throws IOException {
	       String strutturaMenu = null;
	       
	       // GENERAZIONE JSON MENU UTENTE NON LOGGATO
	       if (menuType.equals("not_logged_user_menu")) {
	    	   //Eventualmente si salva il json al DB e non piu su file
		        JSONArray menuJson = getSecondaryMenus(menuType, menuResource);
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
	       }
	       
	       
	       // GENERAZIONE SMALL JSON MENU UTENTE LOGGATO
	       if (menuType.equals("small_user_menu")) {
	    	   //Eventualmente si salva il json al DB e non piu su file
		        JSONArray menuJson = getSecondaryMenus(menuType, menuResource);
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
	       }
	       
	       
	       // GENERAZIONE JSON MENU PER ID RUOLO UTENTE LOGGATO
	       if (menuType.equals("")) {
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
	       }
		return strutturaMenu;
	}
	
	// MENU UTENTE LOGGATO
	@SuppressWarnings("unchecked")
	public List<Object> getMenuByRole_OBJ(int idRuolo) throws IOException {
		String jsonString = getMenuByRoleWSString("", idRuolo);
	
	    ObjectMapper mapper = new ObjectMapper();
	    List<Object> menuList = mapper.readValue(jsonString, List.class);
	    //Map<String, Object>W jsonData = new HashMap<>();
	    //jsonData.put("array", menuList);
	return menuList;
	}
	
	// SMALL MENU UTENTE LOGGATO
	@SuppressWarnings("unchecked")
	public List<Object> getMenuUser() throws IOException {
		String jsonString = getMenuByRoleWSString("small_user_menu", 0);
	
	    ObjectMapper mapper = new ObjectMapper();
	    List<Object> menuList = mapper.readValue(jsonString, List.class);
	    //Map<String, Object>W jsonData = new HashMap<>();
	    //jsonData.put("array", menuList);
	return menuList;
	}
	
	// MENU UTENTE NON LOGGATO
	@SuppressWarnings("unchecked")
	public List<Object> getMenuUserNotLogged() throws IOException {
		String jsonString = getMenuByRoleWSString("not_logged_user_menu", 0);
	
	    ObjectMapper mapper = new ObjectMapper();
	    List<Object> menuList = mapper.readValue(jsonString, List.class);
	    //Map<String, Object>W jsonData = new HashMap<>();
	    //jsonData.put("array", menuList);
	return menuList;
	}

	
	//Lista utenti appartenenti ad un ufficio
    public List<Ufficio> getUfficiUtente(int idUtente) {
        return ufficioRepository.findUfficiByUtenteId(idUtente);
    }
	

	// DA TESTARE!!
    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
    
    
    public List<Ruolo> getRuoliUtenteById(int idUtente) {
        return ruoloRepository.findDistinctRuoliByIdUtente(idUtente);
    }

    // API che restituisce i ruoli di un utente tramite account
    public List<Ruolo> getRuoliUtenteByAccount(String account) {
        return ruoloRepository.findDistinctRuoliByAccount(account);
    }
    
    
    
    /* API PER RESTITUIRE TUTTE LE FUNZIONALITA ASSOCIATE AI RUOLI DELL'UTENTE LOGGATO */

    // Nuovo metodo per ottenere le funzionalità associate ai ruoli di un utente
    public List<Funzionalita> getFunzionalitaByAccount(String account) {
        return ruoloFunzionalitaRepository.findFunzionalitaByAccount(account);
    }

    // Metodo combinato per ottenere ruoli e funzionalità
    public Map<String, Object> getRuoliEFunzionalitaByAccount(String account) {
        List<Ruolo> ruoli = getRuoliUtenteByAccount(account);
        List<Funzionalita> funzionalita = getFunzionalitaByAccount(account);
        
        Map<String, Object> result = new HashMap<>();
        result.put("ruoli", ruoli);
        result.put("funzionalita", funzionalita);
        
        return result;
    }
    
}