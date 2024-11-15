package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.model.Tasso;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TassoFissoEsplicito {

    private Long id;

    private TipologiaTassoEnum tipologiaTasso;

    private Double tassoFinito_10;

    private Double tassoFinito_15;

    private Double tassoFinito_20;

    private Double tassoFinito_25;

    private Double tassoFinito_30;

    public TassoFissoEsplicito(Tasso tasso) {
        this.id = tasso.getId();
        this.tipologiaTasso = tasso.getTipologiaTasso();
        this.tassoFinito_10 = tasso.getTassoFinito_10();
        this.tassoFinito_15 = tasso.getTassoFinito_15();
        this.tassoFinito_20 = tasso.getTassoFinito_20();
        this.tassoFinito_25 = tasso.getTassoFinito_25();
        this.tassoFinito_30 = tasso.getTassoFinito_30();
    }
}