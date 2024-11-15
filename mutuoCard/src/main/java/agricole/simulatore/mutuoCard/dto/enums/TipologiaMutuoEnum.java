package agricole.simulatore.mutuoCard.dto.enums;

import lombok.Getter;

@Getter
public enum TipologiaMutuoEnum {
    CLASSICO("Mutuo Classico"),

    CONSAP("Mutuo Consap");

    private String label;

    TipologiaMutuoEnum(String label) {
    }

}