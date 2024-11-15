package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.enums.RuoliUtenteEnum;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    private RuoliUtenteEnum ruolo;

    @NotBlank(message = "Inserire matricola utente.")
    private String matricola;

    @NotBlank(message = "Inserire dominio utente.")
    private String dominio;
}