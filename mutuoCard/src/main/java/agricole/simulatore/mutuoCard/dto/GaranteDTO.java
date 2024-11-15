package agricole.simulatore.mutuoCard.dto;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GaranteDTO {

    private Integer ndg;

    private String codiceFiscale;

    @NotBlank(message = "Errore: Nome garante mancante.")
    private String nome;

    @NotBlank(message = "Errore: Cognome garante mancante.")
    private String cognome;

    private Double altriRedditi = 0.0;

    private Double impegniFinanziari = 0.0;

    private Double oneriDaAlimenti = 0.0;

    private Double redditiDaLavoro = 0.0;
}