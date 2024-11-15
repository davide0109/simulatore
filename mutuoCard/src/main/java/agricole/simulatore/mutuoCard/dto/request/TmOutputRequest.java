package agricole.simulatore.mutuoCard.dto.request;

import agricole.simulatore.mutuoCard.dto.shared.MutuoCardSoggettoSimulatore;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TmOutputRequest {

    private String idSimulazione;

    private String dominio;

    private String matricola;

    private Integer durataMesi;

    private Integer checkEta;

    private Double mutuoMassimo;

    private Double valoreMinimoImmobile;

    private Double rataSostenibile;

    private String tipoTasso;

    private String garanzia;

    private List<MutuoCardSoggettoSimulatore> intestatari;

    private List<MutuoCardSoggettoSimulatore> garanti;

    private Integer componentiNucleoFamiliare;

    private Integer etaAllaStipula;

    private String provinciaResidenza;

    private Integer sussistenza;

    private Double spese;

    private Double ltiMax;

    private Double rapportoRataRedditoPerc;

    private Double ltvMax;

    private Long indebitamentoLimiteConsap;

    private Double tassoUtilizzatoPerc;

    public TmOutputRequest(OutputRequest outputRequest, String provinciaResidenza) {
        this.idSimulazione = outputRequest.getIdPreventivo();
        this.dominio = outputRequest.getUtente().getDominio();
        this.matricola = outputRequest.getUtente().getMatricola();
        this.durataMesi = (outputRequest.getOutputData().getDurataMesi() * 12);
        this.checkEta = outputRequest.getOutputData().getCheckEta();
        this.mutuoMassimo = outputRequest.getOutputData().getMutuoMassimo();
        this.valoreMinimoImmobile = outputRequest.getOutputData().getValoreMinimoImmobile();
        this.rataSostenibile = outputRequest.getOutputData().getRataSostenibile();
        this.tipoTasso = outputRequest.getOutputData().getTipoTasso().getLabel();
        this.garanzia = Objects.nonNull(outputRequest.getInputData().getGaranzia()) ? outputRequest.getInputData().getGaranzia().toString() : null;
        this.intestatari = outputRequest.getInputData().getIntestatari().stream().map(MutuoCardSoggettoSimulatore::new).collect(Collectors.toList());
        this.garanti = outputRequest.getInputData().getGaranti().stream().map(MutuoCardSoggettoSimulatore::new).collect(Collectors.toList());
        this.componentiNucleoFamiliare = outputRequest.getInputData().getComponentiNucleoFamiliare();
        this.etaAllaStipula = outputRequest.getInputData().getEtaAllaStipula();
        this.provinciaResidenza = provinciaResidenza;
        this.sussistenza = outputRequest.getParametriGenerali().getSussistenza();
        this.spese = outputRequest.getParametriGenerali().getSpese();
        this.ltiMax = outputRequest.getParametriGenerali().getLtiMax();
        this.rapportoRataRedditoPerc = outputRequest.getParametriGenerali().getRapportoRataRedditoPerc();
        this.ltvMax = outputRequest.getParametriGenerali().getLtvMax();
        this.indebitamentoLimiteConsap = outputRequest.getParametriGenerali().getIndebitamentoLimiteConsap();
        this.tassoUtilizzatoPerc = outputRequest.getParametriGenerali().getTassoUtilizzatoPerc();
    }
}