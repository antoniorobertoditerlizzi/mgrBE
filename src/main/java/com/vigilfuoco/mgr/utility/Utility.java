package com.vigilfuoco.mgr.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class Utility {
	
	/* 
	 * Autoconfigurazione delle connessioni al DB tramite DataSourceProperties e 
	 * variabili definite nell'application.properties
	 * 
	 */
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

   /*@Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }*/
    private static final String PREFISSO_TIPO_RICHIESTA = "R"; // REQ - Prefisso per il tipo di richiesta
    private static final int NUMERO_PROGRESSIVO_LENGTH = 4; // Lunghezza del numero progressivo
    //private static final int USER_ID_LENGTH = 4; // Lunghezza dell'ID utente
    //private static final char USER_FILL_SYMBOL = 'X'; // Simbolo di riempimento per userId
    private static final int UFF_ID_LENGTH = 4; // Lunghezza dell'ID ufficio
    private static final char UFF_FILL_SYMBOL = 'Z'; // Simbolo di riempimento per ufficioId
    
    // Genera ISBN Richiesta
    public static String generaNumeroRichiesta(String tipoRichiesta, Long idUtente, Long idUfficio) {
    	
        // Setto Formato Data Corrente
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String data = sdf.format(new Date());

        /*// Creazione oggetto Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Recupero componenti data
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        // Recupero componenti ora
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);*/
        
        // Generazione numero progressivo casuale
        Random random = new Random();
        String numeroProgressivo = String.format("%0" + NUMERO_PROGRESSIVO_LENGTH + "d", random.nextInt((int) Math.pow(10, NUMERO_PROGRESSIVO_LENGTH)));

        // L'ID utente e l'ID ufficio sono lunghi rispettivamente quanto USER_ID_LENGTH e UFF_ID_LENGTH 
        //String userId = String.format("%0" + USER_ID_LENGTH + "d", idUtente.intValue());	//0001 - 01
        //String ufficioId = String.format("%0" + UFF_ID_LENGTH + "d", idUfficio.intValue()); //0001 - 01

        //String userId = String.format("%d%s", idUtente, String.valueOf(USER_FILL_SYMBOL).repeat(USER_ID_LENGTH - String.valueOf(idUtente).length()));
        String ufficioId = String.format("%d%s", idUfficio, String.valueOf(UFF_FILL_SYMBOL).repeat(UFF_ID_LENGTH - String.valueOf(idUfficio).length()));

        
        // Assemblo il Codice
        //String codiceRichiesta = String.format("%s-%s-%s-%s-%s", PREFISSO_TIPO_RICHIESTA, tipoRichiesta.substring(0, 1), data.substring(0, 2), numeroProgressivo.substring(0,3), ufficioId);
        String codiceRichiesta = String.format("%s%s%s%s%s", PREFISSO_TIPO_RICHIESTA, tipoRichiesta.substring(0, 1), data.substring(0, 6), numeroProgressivo.substring(0,3), ufficioId);

        // Check sui 14 caratteri
        if (codiceRichiesta.length() > 14) {
            codiceRichiesta = codiceRichiesta.substring(0, 14);
        }

        return codiceRichiesta;
    }
    
    // Invio mail Richiesta
    public static void invioEmailRichiesta(String numeroRichiesta, String toEmail, String oggetto) {
		String oggettoMail = oggetto + " MGR n." + numeroRichiesta;
		String messaggio = oggetto + " appena effettuata per richiesta n. " + numeroRichiesta + " grazie.";
		 
	    String useMail = PropertiesReader.getProperty("useMail");
	    if (useMail.equals("true")) {
	        String useProxy = PropertiesReader.getProperty("useProxy");
			if (useProxy.equals("true")) {
	            System.out.println("Proxy Abilitato per l'invio e-Mail");
	            MailSender.sendEmail(oggettoMail, messaggio, toEmail);
	        } else if (useProxy.equals("false")) {
	            System.out.println("Proxy Disabilitato per l'invio e-Mail");
	            MailSenderNOPROXY.sendEmail(oggettoMail, messaggio, toEmail);
	        }
	    }
    }
    
}