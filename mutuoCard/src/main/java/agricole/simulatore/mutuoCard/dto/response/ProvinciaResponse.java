package agricole.simulatore.mutuoCard.dto.response;

import agricole.simulatore.mutuoCard.model.Sussistenza;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaResponse {

    private Long id;

    private String nome;

    private String sigla;

    public ProvinciaResponse(Sussistenza sussistenza) {
        this.id = sussistenza.getId();
        this.nome = sussistenza.getProvincia();
        this.sigla = sussistenza.getSigla();
    }
}