package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;

import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputData {

    @NotNull(message = "Errore: Durata in mesi non impostata in fase di richiesta.")
    private Integer durataMesi;

    @NotNull(message = "Errore: Check età non impostato in fase di richiesta.")
    private Integer checkEta;

    @NotNull(message = "Errore: Valore mutuo massimo non impostato in fase di richiesta.")
    private Double mutuoMassimo;

    @NotNull(message = "Errore: Valore minimo immobile non impostato in fase di richiesta.")
    private Double valoreMinimoImmobile;

    @NotNull(message = "Errore: Rata sostenibile non impostata in fase di richiesta.")
    private Double rataSostenibile;

    @NotNull(message = "Errore: Tipo tasso non impostato in fase di richiesta.")
    private TipologiaTassoEnum tipoTasso;
}