package agricole.simulatore.mutuoCard.dto;

import agricole.simulatore.mutuoCard.model.Costante;

import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostanteDTO {

    private Long id;

    @NotNull(message = "Lti Max non può essere nullo!")
    private Double ltiMax;

    @NotNull(message = "Spese non può essere nullo!")
    private Double spese;

    @NotNull(message = "Rapporto Rata Reddito non può essere nullo!")
    private Double rapportoRataReddito;

    @NotNull(message = "Ltv Max non può essere nullo!")
    private Double ltvMax;

    private Double indebitamentoMax;

    public CostanteDTO(Costante costante) {
        this.id = costante.getId();
        this.ltiMax = costante.getLtiMax();
        this.spese = costante.getSpese();
        this.rapportoRataReddito = costante.getRapportoRataReddito();
        this.ltvMax = costante.getLtvMax();
        this.indebitamentoMax = Objects.nonNull(costante.getIndebitamentoMax()) ? costante.getIndebitamentoMax() : null;
    }
}