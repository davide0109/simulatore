package agricole.simulatore.mutuoCard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

import org.apache.commons.csv.CSVRecord;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sussistenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regione;

    private String provincia;

    private String sigla;

    private String zona;

    private Integer numeroFamiliari_1;

    private Integer numeroFamiliari_2;

    private Integer numeroFamiliari_3;

    private Integer numeroFamiliari_4;

    private Integer numeroFamiliari_5;

    private Integer numeroFamiliari_6;

    private Integer numeroFamiliari_7;

    private Integer numeroFamiliari_8;

    private Integer numeroFamiliari_9;

    private Integer numeroFamiliari_10;

    public Sussistenza(CSVRecord csvRecord) {
        this.regione = csvRecord.get("regione");
        this.provincia = csvRecord.get("provincia");
        this.sigla = csvRecord.get("sigla");
        this.zona = csvRecord.get("zona");
        this.numeroFamiliari_1 = Integer.valueOf(csvRecord.get("numeroFamiliari_1"));
        this.numeroFamiliari_2 = Integer.valueOf(csvRecord.get("numeroFamiliari_2"));
        this.numeroFamiliari_3 = Integer.valueOf(csvRecord.get("numeroFamiliari_3"));
        this.numeroFamiliari_4 = Integer.valueOf(csvRecord.get("numeroFamiliari_4"));
        this.numeroFamiliari_5 = Integer.valueOf(csvRecord.get("numeroFamiliari_5"));
        this.numeroFamiliari_6 = Integer.valueOf(csvRecord.get("numeroFamiliari_6"));
        this.numeroFamiliari_7 = Integer.valueOf(csvRecord.get("numeroFamiliari_7"));
        this.numeroFamiliari_8 = Integer.valueOf(csvRecord.get("numeroFamiliari_8"));
        this.numeroFamiliari_9 = Integer.valueOf(csvRecord.get("numeroFamiliari_9"));
        this.numeroFamiliari_10 = Integer.valueOf(csvRecord.get("numeroFamiliari_10"));
    }
}