package agricole.simulatore.mutuoCard.service;

import agricole.simulatore.mutuoCard.dto.enums.RuoliUtenteEnum;
import agricole.simulatore.mutuoCard.dto.request.OutputRequest;
import agricole.simulatore.mutuoCard.dto.request.TmOutputRequest;
import agricole.simulatore.mutuoCard.dto.response.ProvinciaResponse;
import agricole.simulatore.mutuoCard.dto.response.SimulazioneResponse;
import agricole.simulatore.mutuoCard.dto.response.Wso2AuthResponse;
import agricole.simulatore.mutuoCard.dto.response.Wso2OutputResponse;
import agricole.simulatore.mutuoCard.dto.shared.Utente;
import agricole.simulatore.mutuoCard.model.Mutuo;
import agricole.simulatore.mutuoCard.model.Simulazione;
import agricole.simulatore.mutuoCard.model.Sussistenza;
import agricole.simulatore.mutuoCard.model.TmOutputTransaction;
import agricole.simulatore.mutuoCard.repository.TmOutputTransactionRepository;
import agricole.simulatore.mutuoCard.security.SecurityPrincipal;
import agricole.simulatore.mutuoCard.service.entityService.MutuoService;
import agricole.simulatore.mutuoCard.service.entityService.SimulazioneService;
import agricole.simulatore.mutuoCard.service.entityService.SussistenzaService;
import agricole.simulatore.mutuoCard.utils.exception.WebClient400Exception;
import agricole.simulatore.mutuoCard.utils.exception.WebClient500Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GestioneSimulazioneService {

    @Autowired
    private SussistenzaService sussistenzaService;

    @Autowired
    private MutuoService mutuoService;

    @Autowired
    private SimulazioneService simulazioneService;

    @Autowired
    private WebClientService webClientService;

    @Autowired
    private TmOutputTransactionRepository transactionRepository;

    public List<ProvinciaResponse> getProvince() {
        return sussistenzaService.findAll().stream().map(ProvinciaResponse::new).collect(Collectors.toList()).stream().sorted(Comparator.comparing(p -> p.getNome().replace("'", ""))).collect(Collectors.toList());
    }

    public Integer getSussistenza(String provincia, Integer numeroComponenti) {
        Sussistenza sussistenza = sussistenzaService.findByProvincia(provincia);
        switch (numeroComponenti) {
            case 1:
                return sussistenza.getNumeroFamiliari_1();
            case 2:
                return sussistenza.getNumeroFamiliari_2();
            case 3:
                return sussistenza.getNumeroFamiliari_3();
            case 4:
                return sussistenza.getNumeroFamiliari_4();
            case 5:
                return sussistenza.getNumeroFamiliari_5();
            case 6:
                return sussistenza.getNumeroFamiliari_6();
            case 7:
                return sussistenza.getNumeroFamiliari_7();
            case 8:
                return sussistenza.getNumeroFamiliari_8();
            case 9:
                return sussistenza.getNumeroFamiliari_9();
            case 10:
                return sussistenza.getNumeroFamiliari_10();
            default:
                throw new IllegalArgumentException("Numero componenti non valido! - " + numeroComponenti);
        }
    }

    public void sendOutput(OutputRequest request) {
        setSimulazione(request);

        TmOutputRequest tmOutputRequest = new TmOutputRequest(request, sussistenzaService.findByProvincia(request.getInputData().getProvinciaResidenza()).getSigla());
        TmOutputTransaction transaction = transactionRepository.save(new TmOutputTransaction(tmOutputRequest.toString(), LocalDateTime.now()));
        try {
            Wso2AuthResponse responseToken = webClientService.postWso2Token(Wso2AuthResponse.class);
            Wso2OutputResponse response = webClientService.postWso2Output(tmOutputRequest, Wso2OutputResponse.class, responseToken);
            if (response.getRetCode().equalsIgnoreCase("-1")) throw new WebClient500Exception(response.getRetMessage());
            if (response.getRetCode().equalsIgnoreCase("1")) throw new WebClient400Exception(response.getRetMessage());
            transaction.setResponse(response.toString());
            transaction.setStatusCode(HttpStatus.OK.value());
        } catch (WebClient400Exception ex) {
            transaction.setResponse(ex.toString());
            transaction.setStatusCode(400);
            throw ex;
        } catch (WebClient500Exception ex) {
            transaction.setResponse(ex.toString());
            transaction.setStatusCode(500);
            throw ex;
        } finally {
            transaction.setResponseDate(LocalDateTime.now());
            transactionRepository.save(transaction);
        }
    }

    public SimulazioneResponse getSimulazione() {
        try {
            SecurityPrincipal principal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (Objects.nonNull(principal.getIdPreventivo())) {
                Simulazione simulazione = simulazioneService.findByIdPreventivo(principal.getIdPreventivo());
                return new SimulazioneResponse(simulazione, new Utente(RuoliUtenteEnum.valueOf(principal.getUserType()), principal.getMatricola(), principal.getDominio()), principal.getIdPreventivo());
            }
            return new SimulazioneResponse(new Utente(RuoliUtenteEnum.valueOf(principal.getUserType()), principal.getMatricola(), principal.getDominio()), Objects.nonNull(principal.getIdPreventivo()) ? principal.getIdPreventivo() : null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tale metodo aggiorna o crea un simulazione a database.
     * Se l'id preventivo esiste a database, viene aggiornata la simulazione esistente, altrimenti viene creata.
     *
     * @param request dati da inviare a TM.
     */
    public void setSimulazione(OutputRequest request) {
        Mutuo mutuo = mutuoService.read(request.getInputData().getIdMutuo());
        if (simulazioneService.existByIdPreventivo(request.getIdPreventivo()))
            simulazioneService.update(new Simulazione(request, mutuo, simulazioneService.findByIdPreventivo(request.getIdPreventivo()).getId()));
        else simulazioneService.create(new Simulazione(request, mutuo, null));
    }
}