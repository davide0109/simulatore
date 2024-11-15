package agricole.simulatore.mutuoCard.dto.enums;

import lombok.Getter;

@Getter
public enum TipologiaGaranziaConsapEnum {

    PRIORITARIO_80("Prioritario 80%"),

    PRIORITARIO_50("Prioritario 50%"),

    NON_PRIORITARIO_50("Non Prioritario 50%");

    private String label;

    TipologiaGaranziaConsapEnum(String label) {
    }
}