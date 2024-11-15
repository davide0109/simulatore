package agricole.simulatore.mutuoCard.dto.request;

import agricole.simulatore.mutuoCard.dto.shared.Costanti;
import agricole.simulatore.mutuoCard.dto.shared.InputData;
import agricole.simulatore.mutuoCard.dto.shared.OutputData;
import agricole.simulatore.mutuoCard.dto.shared.Utente;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputRequest {

    @NotNull(message = "Errore: L'oggetto utente non può essere null.")
    @Valid
    private Utente utente;

    private Long idSimulazione;

    @NotBlank(message = "Errore: Id preventivo non impostato in fase di richiesta.")
    private String idPreventivo;

    @NotNull(message = "Errore: L'oggetto output data non può essere null.")
    @Valid
    private OutputData outputData;

    @NotNull(message = "Errore: L'oggetto input data non può essere null.")
    @Valid
    private InputData inputData;

    @NotNull(message = "Errore: L'oggetto parametri generali non può essere null.")
    @Valid
    private Costanti parametriGenerali;
}