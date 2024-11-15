package agricole.simulatore.mutuoCard.dto.response;

import agricole.simulatore.mutuoCard.dto.shared.InputData;
import agricole.simulatore.mutuoCard.dto.shared.Utente;
import agricole.simulatore.mutuoCard.model.Simulazione;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulazioneResponse {

    private Utente utente;

    private Long idSimulazione;

    private String idPreventivo;

    private InputData inputData;

    public SimulazioneResponse(Simulazione simulazione, Utente utente, String preventivo) {
        this.utente = utente;
        this.idSimulazione = Objects.nonNull(simulazione) ? simulazione.getId() : null;
        this.idPreventivo = preventivo;
        this.inputData = Objects.nonNull(simulazione) ? new InputData(simulazione) : null;
    }

    public SimulazioneResponse(Utente utente, String preventivo) {
        this.utente = utente;
        this.idPreventivo = preventivo;
    }
}