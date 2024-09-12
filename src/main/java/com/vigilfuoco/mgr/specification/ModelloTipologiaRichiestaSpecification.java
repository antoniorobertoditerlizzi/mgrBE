package com.vigilfuoco.mgr.specification;

import org.springframework.data.jpa.domain.Specification;

import com.vigilfuoco.mgr.model.ModelloTipologiaRichiesta;

public class ModelloTipologiaRichiestaSpecification {

    public static Specification<ModelloTipologiaRichiesta> hasTipologiaRichiestaId(Short idTipologiaRichiesta) {
        return (root, query, builder) -> {
            if (idTipologiaRichiesta == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("tipologiaRichiesta").get("idTipologiaRichiesta"), idTipologiaRichiesta);
        };
    }

    public static Specification<ModelloTipologiaRichiesta> hasModelloId(Long idModello) {
        return (root, query, builder) -> {
            if (idModello == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("modello").get("idModello"), idModello);
        };
    }
}
