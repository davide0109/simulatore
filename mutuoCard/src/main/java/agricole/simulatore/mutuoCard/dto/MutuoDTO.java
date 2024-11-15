package agricole.simulatore.mutuoCard.dto;

import agricole.simulatore.mutuoCard.dto.enums.DominiConsapEnum;
import agricole.simulatore.mutuoCard.dto.shared.TassoFissoEsplicito;
import agricole.simulatore.mutuoCard.dto.shared.TassoParametrato;
import agricole.simulatore.mutuoCard.model.Mutuo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutuoDTO {

    private Long id;

    @NotBlank(message = "Il nome del nuovo mutuo non può essere nullo o vuoto!")
    @Size(min = 1, max = 100)
    private String nome;

    private TassoFissoEsplicito tassoFissoEsplicito;

    private TassoParametrato fissoParametrato;

    private TassoParametrato variabile;

    private TassoParametrato variabileCap;

    @Valid
    @NotNull(message = "L'oggetto costanti non può essere nullo!")
    private CostanteDTO costanti;

    private DominiConsapEnum garanzia;

    private Boolean isClassic;

    private Boolean isActive;

    private Boolean pdf;

    public MutuoDTO(Mutuo mutuo) {
        this.id = mutuo.getId();
        this.nome = mutuo.getNome();
        this.tassoFissoEsplicito = Objects.nonNull(mutuo.getFissoEsplicito()) ? new TassoFissoEsplicito(mutuo.getFissoEsplicito()) : null;
        this.fissoParametrato = Objects.nonNull(mutuo.getFissoParametrato()) ? new TassoParametrato(mutuo.getFissoParametrato()) : null;
        this.variabile = Objects.nonNull(mutuo.getVariabile()) ? new TassoParametrato(mutuo.getVariabile()) : null;
        this.variabileCap = Objects.nonNull(mutuo.getVariabileCap()) ? new TassoParametrato(mutuo.getVariabileCap()) : null;
        this.costanti = new CostanteDTO(mutuo.getCostanti());
        this.isClassic = mutuo.getIsClassic();
        this.isActive = mutuo.getIsActive();
        this.pdf = Objects.nonNull(mutuo.getFilePdf());
        this.garanzia = mutuo.getGaranzia();
    }
}