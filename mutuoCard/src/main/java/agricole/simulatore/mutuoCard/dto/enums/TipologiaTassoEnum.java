package agricole.simulatore.mutuoCard.dto.enums;

import lombok.Getter;

@Getter
public enum TipologiaTassoEnum {
    FISSO_ESPLICITO("F"),

    FISSO_PARAMETRICO("F"),

    VARIABILE("V"),

    VARIABILE_CAP("C");

    private final String label;

    TipologiaTassoEnum(String label) {
        this.label = label;
    }
}