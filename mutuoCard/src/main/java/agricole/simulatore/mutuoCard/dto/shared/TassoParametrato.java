package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.model.Tasso;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TassoParametrato {

    private Long id;

    private TipologiaTassoEnum tipologiaTasso;

    private Double spread_10;

    private Double spread_15;

    private Double spread_20;

    private Double spread_25;

    private Double spread_30;

    public TassoParametrato(Tasso tasso) {
        this.id = tasso.getId();
        this.tipologiaTasso = tasso.getTipologiaTasso();
        this.spread_10 = tasso.getSpread_10();
        this.spread_15 = tasso.getSpread_15();
        this.spread_20 = tasso.getSpread_20();
        this.spread_25 = tasso.getSpread_25();
        this.spread_30 = tasso.getSpread_30();
    }
}