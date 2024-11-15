package agricole.simulatore.mutuoCard.dto.response;

import agricole.simulatore.mutuoCard.dto.MutuoDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListinoResponse {

    private Long id;

    private String nome;

    private LocalDateTime createdDate;

    private List<MutuoDTO> mutuoList;

    private List<ParametroResponse> parametroList;
}