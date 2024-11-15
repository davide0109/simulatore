package agricole.simulatore.mutuoCard.model;

import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.dto.shared.TassoFissoEsplicito;
import agricole.simulatore.mutuoCard.dto.shared.TassoParametrato;

import javax.persistence.*;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasso extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipologiaTassoEnum tipologiaTasso;

    private Double spread_10;

    private Double tassoFinito_10;

    private Double spread_15;

    private Double tassoFinito_15;

    private Double spread_20;

    private Double tassoFinito_20;

    private Double spread_25;

    private Double tassoFinito_25;

    private Double spread_30;

    private Double tassoFinito_30;

    public Tasso(TassoFissoEsplicito tassoFissoEsplicito) {
        this.tipologiaTasso = TipologiaTassoEnum.FISSO_ESPLICITO;
        this.tassoFinito_10 = tassoFissoEsplicito.getTassoFinito_10();
        this.tassoFinito_15 = tassoFissoEsplicito.getTassoFinito_15();
        this.tassoFinito_20 = tassoFissoEsplicito.getTassoFinito_20();
        this.tassoFinito_25 = tassoFissoEsplicito.getTassoFinito_25();
        this.tassoFinito_30 = tassoFissoEsplicito.getTassoFinito_30();
    }

    public Tasso(TassoParametrato tassoParametrato, TipologiaTassoEnum tipologiaTassoEnum) {
        this.tipologiaTasso = tipologiaTassoEnum;
        this.spread_10 = tassoParametrato.getSpread_10();
        this.spread_15 = tassoParametrato.getSpread_15();
        this.spread_20 = tassoParametrato.getSpread_20();
        this.spread_25 = tassoParametrato.getSpread_25();
        this.spread_30 = tassoParametrato.getSpread_30();
    }
}