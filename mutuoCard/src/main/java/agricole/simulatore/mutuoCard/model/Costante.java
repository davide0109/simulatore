package agricole.simulatore.mutuoCard.model;

import agricole.simulatore.mutuoCard.dto.CostanteDTO;

import javax.persistence.*;

import lombok.*;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Costante extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double ltiMax;

    @Column(nullable = false)
    private Double spese;

    @Column(nullable = false)
    private Double rapportoRataReddito;

    @Column(nullable = false)
    private Double ltvMax;

    private Double indebitamentoMax;

    public Costante(CostanteDTO costanteDTO) {
        this.ltiMax = costanteDTO.getLtiMax();
        this.spese = costanteDTO.getSpese();
        this.rapportoRataReddito = costanteDTO.getRapportoRataReddito();
        this.ltvMax = costanteDTO.getLtvMax();
        this.indebitamentoMax = Objects.nonNull(costanteDTO.getIndebitamentoMax()) ? costanteDTO.getIndebitamentoMax() : null;
    }
}