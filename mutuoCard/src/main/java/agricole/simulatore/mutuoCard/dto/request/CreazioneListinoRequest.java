package agricole.simulatore.mutuoCard.dto.request;

import agricole.simulatore.mutuoCard.dto.MutuoDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreazioneListinoRequest {

    @NotBlank(message = "Inserire nome listino!")
    private String nomeListino;

    @NotEmpty
    @NotNull
    @Valid
    private List<MutuoDTO> mutuoList;
}