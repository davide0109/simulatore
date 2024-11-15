package agricole.simulatore.mutuoCard.dto;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IntestatarioDTO {

    private Integer ndg;

    private String codiceFiscale;

    @NotBlank(message = "Errore: Nome intestatario mancante.")
    private String nome;

    @NotBlank(message = "Errore: Cognome intestatario mancante.")
    private String cognome;

    private Double altriRedditi = 0.0;

    private Double impegniFinanziari = 0.0;

    private Double oneriDaAlimenti = 0.0;

    private Double redditiDaLavoro = 0.0;

    @NotNull(message = "Errore: Impostare boolean isFirst.")
    private Boolean isFirst;
}