package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.GaranteDTO;
import agricole.simulatore.mutuoCard.dto.IntestatarioDTO;
import agricole.simulatore.mutuoCard.dto.enums.DominiConsapEnum;
import agricole.simulatore.mutuoCard.model.Simulazione;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputData {

    @NotNull(message = "Errore: Id mutuo non impostato in fase di richiesta.")
    private Long idMutuo;

    private DominiConsapEnum garanzia;

    @NotEmpty(message = "Errore: Inserire almeno un intestatario.")
    @Valid
    private List<IntestatarioDTO> intestatari;

    @Valid
    private List<GaranteDTO> garanti;

    @NotNull(message = "Errore: Componenti nucleo familiare non impostato in fase di richiesta.")
    private Integer componentiNucleoFamiliare;

    @NotNull(message = "Errore: Età alla stipula non impostata in fase di richiesta.")
    private Integer etaAllaStipula;

    @NotBlank(message = "Errore: Provincia di residenza non impostata in fase di richiesta.")
    private String provinciaResidenza;

    public InputData(Simulazione simulazione) {
        this.idMutuo = simulazione.getMutuo().getId();
        this.garanzia = simulazione.getMutuo().getGaranzia();
        this.intestatari = simulazione.getIntestatari().stream().sorted(Comparator.comparing(IntestatarioDTO::getIsFirst).reversed()).collect(Collectors.toList());
        this.garanti = new ArrayList<>(simulazione.getGaranti());
        this.componentiNucleoFamiliare = simulazione.getComponentiNucleoFamiliare();
        this.etaAllaStipula = simulazione.getEtaAllaStipula();
        this.provinciaResidenza = simulazione.getProvinciaResidenza();
    }
}