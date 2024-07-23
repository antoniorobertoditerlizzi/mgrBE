package com.vigilfuoco.mgr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Per gestire l'eccezione e restituire una risposta appropriata al client. 
//Gestore di eccezioni nel controller.

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NumeroRichiestaDuplicatoException.class)
    public ResponseEntity<String> handleNumeroRichiestaDuplicatoException(NumeroRichiestaDuplicatoException ex) {
    	logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}