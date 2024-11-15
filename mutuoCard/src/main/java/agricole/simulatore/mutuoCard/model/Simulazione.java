package agricole.simulatore.mutuoCard.model;

import agricole.simulatore.mutuoCard.dto.GaranteDTO;
import agricole.simulatore.mutuoCard.dto.IntestatarioDTO;
import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.dto.request.OutputRequest;

import javax.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulazione extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idPreventivo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "simulazione_intestatari", joinColumns = @JoinColumn(name = "id"))
    private Set<IntestatarioDTO> intestatari;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "simulazione_garanti", joinColumns = @JoinColumn(name = "id"))
    private Set<GaranteDTO> garanti;

    private Integer componentiNucleoFamiliare;

    private Integer etaAllaStipula;

    private String provinciaResidenza;

    @ManyToOne
    @JoinColumn(name = "mutuo_id")
    private Mutuo mutuo;

    @ManyToOne
    @JoinColumn(name = "listino_id")
    private Listino listino;

    private TipologiaTassoEnum tipologiaTassoEnum;

    public Simulazione(OutputRequest request, Mutuo mutuo, Long id) {
        this.id = id;
        this.idPreventivo = request.getIdPreventivo();
        this.intestatari = new HashSet<>(request.getInputData().getIntestatari());
        this.garanti =  new HashSet<>(request.getInputData().getGaranti());
        this.componentiNucleoFamiliare = request.getInputData().getComponentiNucleoFamiliare();
        this.etaAllaStipula = request.getInputData().getEtaAllaStipula();
        this.provinciaResidenza = request.getInputData().getProvinciaResidenza();
        this.mutuo = mutuo;
        this.listino = mutuo.getListino();
        this.tipologiaTassoEnum = request.getOutputData().getTipoTasso();
    }
}