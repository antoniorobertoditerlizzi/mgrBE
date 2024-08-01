package com.vigilfuoco.mgr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vigilfuoco.mgr.exception.InvalidRequestStateException;
import com.vigilfuoco.mgr.exception.MenuException;
import com.vigilfuoco.mgr.exception.NumeroRichiestaDuplicatoException;
import com.vigilfuoco.mgr.exception.ResourceNotFoundException;
import com.vigilfuoco.mgr.exception.RichiestaException;
import com.vigilfuoco.mgr.exception.RichiestaNotFoundException;

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

    // PER MODIFICA RICHIESTA
    @ExceptionHandler(RichiestaNotFoundException.class)
    public ResponseEntity<String> handleRichiestaNotFoundException(RichiestaNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RichiestaException.class)
    public ResponseEntity<String> handleRichiestaException(RichiestaException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("An unexpected error occurred", ex);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    //PER CANCELLAZIONE RICHIESTA
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestStateException.class)
    public ResponseEntity<String> handleInvalidRequestStateException(InvalidRequestStateException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MenuException.class)
    public ResponseEntity<String> handleMenuException(MenuException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}