package com.vigilfuoco.mgr.specification;

import com.vigilfuoco.mgr.model.Richiesta;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

public class RichiestaSpecification {

	//NOTE: Se id è null, ritorna una conjunction(), che è una condizione sempre vera. Questo significa che questo filtro non avrà alcun effetto se id è null.
		  //Se id non è null, costruisce una condizione equal, che corrisponde al campo idRichiesta dell'entità Richiesta.
		  //Condizioni combinabili
	
    public static Specification<Richiesta> hasId(Long id) {
        return (root, query, builder) ->
                id == null ? builder.conjunction() : builder.equal(root.get("idRichiesta"), id);
    }

    public static Specification<Richiesta> hasNumeroRichiesta(String numeroRichiesta) {
        return (root, query, builder) ->
                numeroRichiesta == null ? builder.conjunction() : builder.equal(root.get("numeroRichiesta"), numeroRichiesta);
    }

    public static Specification<Richiesta> hasIdStatoRichiesta(Long idStatoRichiesta) {
        return (root, query, builder) ->
                idStatoRichiesta == null ? builder.conjunction() : builder.equal(root.get("statoRichiesta").get("idStatoRichiesta"), idStatoRichiesta);
    }

    public static Specification<Richiesta> hasDescrizioneStatoRichiesta(String descrizioneStatoRichiesta) {
        return (root, query, builder) ->
                descrizioneStatoRichiesta == null ? builder.conjunction() : builder.like(root.get("statoRichiesta").get("descrizioneStatoRichiesta"), "%" + descrizioneStatoRichiesta + "%");
    }

    public static Specification<Richiesta> isAttivo(Boolean attivo) {
        return (root, query, builder) ->
                attivo == null ? builder.conjunction() : builder.equal(root.get("statoRichiesta").get("attivo"), attivo);
    }

    public static Specification<Richiesta> hasIdSettoreUfficio(Short idSettoreUfficio) {
        return (root, query, builder) ->
                idSettoreUfficio == null ? builder.conjunction() : builder.equal(root.get("settoreUfficio").get("idSettoreUfficio"), idSettoreUfficio);
    }

    public static Specification<Richiesta> hasIdUfficio(Long idUfficio) {
        return (root, query, builder) ->
                idUfficio == null ? builder.conjunction() : builder.equal(root.get("settoreUfficio").get("ufficio").get("idUfficio"), idUfficio);
    }
    
    public static Specification<Richiesta> hasIdUtenteUfficioRuoloStatoCorrente(Long idUtenteUfficioRuoloStatoCorrente) {
        return (root, query, builder) -> 
            idUtenteUfficioRuoloStatoCorrente == null ? builder.conjunction() : builder.equal(root.get("utenteUfficioRuoloStatoCorrente").get("idUtenteUfficioRuolo"), idUtenteUfficioRuoloStatoCorrente);
    }

    public static Specification<Richiesta> hasIdUtenteUfficioRuoloStatoIniziale(Long idUtenteUfficioRuoloStatoIniziale) {
        return (root, query, builder) -> 
            idUtenteUfficioRuoloStatoIniziale == null ? builder.conjunction() : builder.equal(root.get("utenteUfficioRuoloStatoIniziale").get("idUtenteUfficioRuolo"), idUtenteUfficioRuoloStatoIniziale);
    }
    
    // Join con la tabella SettoreUfficio e successivo Join con Ufficio
    public static Specification<Richiesta> hasDescrizioneUfficio(String descrizioneUfficio) {
        return (root, query, builder) -> {
            if (descrizioneUfficio == null) {
                return builder.conjunction();
            }
            // Eseguo join con la tabella SettoreUfficio e successivo Join con Ufficio
            return builder.like(root.join("settoreUfficio").join("ufficio").get("descrizioneUfficio"), "%" + descrizioneUfficio + "%");
        };
    }
    
    // Join con tbl_utenti_uffici_ruoli e successivamente con tbl_utenti
    public static Specification<Richiesta> hasIdUtente(Long idUtente) {
        return (root, query, builder) -> {
            if (idUtente == null) {
                return builder.conjunction();
            }
            // da rivedere LEFT JOIN per utente
            return builder.equal(
                root.join("utenteUfficioRuoloStatoCorrente", JoinType.LEFT)  // da rivedere LEFT JOIN per evitare che vengano esclusi record
                    .join("utente", JoinType.LEFT)  // da rivedere LEFT JOIN su tbl_utenti
                    .get("idUtente"), idUtente
            );
        };
    }
    
    
    
}
