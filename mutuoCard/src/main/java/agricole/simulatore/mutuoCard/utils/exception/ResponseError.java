package agricole.simulatore.mutuoCard.utils.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    @Schema(description = "Messaggio di errore, talvolta criptico e ogni tanto null")
    private String message;
}
