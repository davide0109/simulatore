package agricole.simulatore.mutuoCard.dto.response;

import agricole.simulatore.mutuoCard.model.Listino;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OldListiniResponse {

    private Long idListino;

    private String nome;

    private LocalDateTime createdDate;

    public OldListiniResponse(Listino listino) {
        this.idListino = listino.getId();
        this.nome = listino.getNome();
        this.createdDate = listino.getCreatedDate().orElse(null);
    }
}