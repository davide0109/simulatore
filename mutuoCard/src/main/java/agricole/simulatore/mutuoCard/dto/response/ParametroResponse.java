package agricole.simulatore.mutuoCard.dto.response;

import agricole.simulatore.mutuoCard.dto.enums.AnniEnum;

import agricole.simulatore.mutuoCard.model.VistaParametri;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametroResponse {

    private String cod_tasso;

    private Double value_tasso;

    private AnniEnum tipoParametro;

    public ParametroResponse(VistaParametri vistaParametri) {
        this.cod_tasso = vistaParametri.getCodTasso();
        this.value_tasso = Double.valueOf(vistaParametri.getValueTasso().replace(",",".")); //Sulla vista il value tasso viene gestito come stringa e viene usata la virgola come separatore decimale.
        this.tipoParametro = AnniEnum.fromCod(vistaParametri.getCodTasso());
    }
}