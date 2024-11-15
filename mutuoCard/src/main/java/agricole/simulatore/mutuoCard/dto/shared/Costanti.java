package agricole.simulatore.mutuoCard.dto.shared;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Costanti {

    private Integer sussistenza;

    private Double spese;

    private Double ltiMax;

    private Double RapportoRataRedditoPerc;

    private Double ltvMax;

    private Long indebitamentoLimiteConsap;

    private Double tassoUtilizzatoPerc;
}