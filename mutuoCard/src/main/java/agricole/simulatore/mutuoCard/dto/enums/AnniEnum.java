package agricole.simulatore.mutuoCard.dto.enums;

import lombok.Getter;

@Getter
public enum AnniEnum {

    IRS_10("0041"),
    IRS_15("0042"),
    IRS_20("0043"),
    IRS_25("0044"),
    IRS_30("0045"),
    EURIBOR("0027"),
    EURIBOR_CAP("0207");

    private final String cod;

    AnniEnum(String cod) {
        this.cod = cod;
    }

    public static AnniEnum fromCod(String cod) {
        AnniEnum[] var1 = values();

        for (AnniEnum anniEnum : var1) {
            if (anniEnum.getCod().equalsIgnoreCase(cod)) {
                return anniEnum;
            }
        }
        return null;
    }
}