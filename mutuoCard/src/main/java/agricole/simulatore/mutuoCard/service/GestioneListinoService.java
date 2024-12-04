package agricole.simulatore.mutuoCard.service;

import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.dto.request.CreazioneListinoRequest;
import agricole.simulatore.mutuoCard.dto.MutuoDTO;
import agricole.simulatore.mutuoCard.dto.response.ListinoResponse;
import agricole.simulatore.mutuoCard.dto.response.OldListiniResponse;
import agricole.simulatore.mutuoCard.dto.response.ParametroResponse;
import agricole.simulatore.mutuoCard.dto.shared.File;
import agricole.simulatore.mutuoCard.dto.shared.TassoFissoEsplicito;
import agricole.simulatore.mutuoCard.dto.shared.TassoParametrato;
import agricole.simulatore.mutuoCard.model.Costante;
import agricole.simulatore.mutuoCard.model.Listino;
import agricole.simulatore.mutuoCard.model.Mutuo;
import agricole.simulatore.mutuoCard.model.Tasso;
import agricole.simulatore.mutuoCard.service.entityService.*;
import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;
import agricole.simulatore.mutuoCard.utils.messaging.Message;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GestioneListinoService {

    @Autowired
    private ListinoService listinoService;

    @Autowired
    private MutuoService mutuoService;

    @Autowired
    private TassoService tassoService;

    @Autowired
    private CostanteService costanteService;

    @Autowired
    private VistaParametriService vistaParametriService;

    /**
     * Salva a db la configurazione di start dei listini se count == 0.
     */
    @PostConstruct
    public void init() {
        if (listinoService.count() == 0) {
            List<MutuoDTO> mutuoDTOList = new ArrayList<>();
            mutuoDTOList.add(new MutuoDTO(Boolean.TRUE));
            creaListino(new CreazioneListinoRequest("Init: " + LocalDate.now(), mutuoDTOList));
        }
    }

    public void creaListino(CreazioneListinoRequest request) {
        Listino listino = listinoService.create(new Listino(Boolean.FALSE, request.getNomeListino()));
        List<Mutuo> mutuoList;
        try {
            mutuoList = request.getMutuoList().stream().map(this::creaMutuo).collect(Collectors.toList());
        } catch (Exception e) {
            mutuoService.deleteByListinoIsNull();
            listinoService.delete(listino);
            throw new RuntimeException(e.getMessage());
        }
        mutuoList.forEach(mutuo -> {
            mutuo.setListino(listino);
            mutuoService.update(mutuo);
        });
        try {
            Listino oldListino = listinoService.findByIsActiveIsTrue();
            oldListino.setIsActive(Boolean.FALSE);
            listinoService.update(oldListino);
        } catch (ResourceNotFoundException ignored) {
        } finally {
            listino.setIsActive(Boolean.TRUE);
            listinoService.update(listino);
        }
    }

    public Mutuo creaMutuo(MutuoDTO request) {
        File file = null;
        validazioneMutuoRequest(request);
        List<Tasso> tassi = creaTassi(request.getTassoFissoEsplicito(), request.getFissoParametrato(), request.getVariabile(), request.getVariabileCap());
        if (Objects.nonNull(request.getId()) && request.getPdf()) file = new File(mutuoService.read(request.getId()));
        return mutuoService.create(new Mutuo(request.getNome(), file, tassi, costanteService.create(new Costante(request.getCostanti())), request.getIsClassic(), request.getIsActive(), request.getGaranzia()));
    }

    public List<Tasso> creaTassi(TassoFissoEsplicito esplicito, TassoParametrato fissoParametrato, TassoParametrato variabile, TassoParametrato variabileCap) {
        List<Tasso> tassi = new ArrayList<>();
        if (Objects.nonNull(esplicito))
            tassi.add(tassoService.create(new Tasso(esplicito)));
        if (Objects.nonNull(fissoParametrato))
            tassi.add(tassoService.create(new Tasso(fissoParametrato, TipologiaTassoEnum.FISSO_PARAMETRICO)));
        if (Objects.nonNull(variabile))
            tassi.add(tassoService.create(new Tasso(variabile, TipologiaTassoEnum.VARIABILE)));
        if (Objects.nonNull(variabileCap))
            tassi.add(tassoService.create(new Tasso(variabileCap, TipologiaTassoEnum.VARIABILE_CAP)));
        return tassi;
    }

    public void validazioneMutuoRequest(MutuoDTO request) {
        if (Objects.isNull(request.getTassoFissoEsplicito()) && Objects.isNull(request.getFissoParametrato()) && Objects.isNull(request.getVariabile()) && Objects.isNull(request.getVariabileCap()))
            throw new ValidationException("Errore durante la creazione del mutuo: Almeno un tipo di tasso va inserito! - Mutuo: " + request.getNome());
        if (Objects.isNull(request.getCostanti()) || Objects.isNull(request.getCostanti().getLtiMax()) || Objects.isNull(request.getCostanti().getLtvMax()) || Objects.isNull(request.getCostanti().getSpese()) || Objects.isNull(request.getCostanti().getRapportoRataReddito()))
            throw new ValidationException("Errore durante la creazione del mutuo: Inserire tutte le costanti! Mutuo: " + request.getNome());
    }

    public ListinoResponse getListino() {
        Listino listino = listinoService.findByIsActiveIsTrue();
        List<MutuoDTO> mutuoDTOList = mutuoService.findByListino(listino).stream().filter(mutuo -> !mutuo.getIsDeleted()).map(MutuoDTO::new).sorted(Comparator.comparing(MutuoDTO::getIsClassic).reversed()).collect(Collectors.toList());
        return new ListinoResponse(listino.getId(), listino.getNome(), listino.getCreatedDate().orElse(null), mutuoDTOList, getParametri());
    }

    public List<OldListiniResponse> getListini() {
        return listinoService.getLast10InactiveListini().stream().map(OldListiniResponse::new).collect(Collectors.toList());
    }

    public void ripristinaListino(Long idListino) {
        Listino oldActive = listinoService.findByIsActiveIsTrue();
        Listino newActive = listinoService.read(idListino);
        oldActive.setIsActive(Boolean.FALSE);
        newActive.setIsActive(Boolean.TRUE);
        listinoService.update(oldActive);
        listinoService.update(newActive);
    }

    public ResponseEntity<Message> deleteTipologiaMutuo(Long idMutuo) {
        Mutuo mutuo = mutuoService.read(idMutuo);
        if (mutuo.getIsClassic())
            return new ResponseEntity<>(new Message("Questa tipologia mutuo non può essere eliminata!", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        mutuo.setIsDeleted(Boolean.TRUE);
        mutuoService.update(mutuo);
        return new ResponseEntity<>(new Message("Tipologia mutuo eliminata con successo!", HttpStatus.OK.value(), LocalDateTime.now().toString()), HttpStatus.OK);
    }

    public ResponseEntity<Message> activeOrDisableTipologiaMutuo(Long idMutuo) {
        Mutuo mutuo = mutuoService.read(idMutuo);
        if (mutuo.getIsClassic())
            return new ResponseEntity<>(new Message("Questa tipologia mutuo non può essere disabilitata!", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        mutuo.setIsActive(!mutuo.getIsActive());
        mutuoService.update(mutuo);
        return new ResponseEntity<>(new Message("Tipologia mutuo modificata con successo!", HttpStatus.OK.value(), LocalDateTime.now().toString()), HttpStatus.OK);
    }

    public List<ParametroResponse> getParametri() {
        return vistaParametriService.getParametri(Arrays.asList("0041", "0042", "0043", "0044", "0045", "0027", "0207"));
    }
}