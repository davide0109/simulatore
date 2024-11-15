package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.dto.request.CreazioneListinoRequest;
import agricole.simulatore.mutuoCard.dto.response.ListinoResponse;
import agricole.simulatore.mutuoCard.dto.response.OldListiniResponse;
import agricole.simulatore.mutuoCard.dto.response.ParametroResponse;
import agricole.simulatore.mutuoCard.service.GestioneListinoService;
import agricole.simulatore.mutuoCard.utils.messaging.Message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/gestione-listino")
@Tag(name = "Listino Controller", description = "Api per la gestione dei listini e delle tipologie mutuo ad essi collegate.")
public class GestioneListinoController {

    @Autowired
    private GestioneListinoService gestioneListinoService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Api per la creazione di un nuovo listino mutui.", summary = "Crea nuovo listino.")
    public ResponseEntity<Message> creaListino(@RequestBody @Valid CreazioneListinoRequest request) {
        gestioneListinoService.creaListino(request);
        return new ResponseEntity<>(new Message("Nuovo Listino pubblicato con successo!", HttpStatus.CREATED.value(), LocalDateTime.now().toString()), HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(description = "Tale API restituisce il listino mutui attivo.", summary = "Get listino attivo.")
    public ResponseEntity<ListinoResponse> getListino() {
        return ResponseEntity.ok(gestioneListinoService.getListino());
    }

    @GetMapping("/listini")
    @Operation(description = "Tale API restituisce i listini passati, non attivi, per un eventuale ripristino di uno di essi.", summary = "Get listini non attivi.")
    public ResponseEntity<List<OldListiniResponse>> getListini() {
        return ResponseEntity.ok(gestioneListinoService.getListini());
    }

    @PutMapping("/ripristino/{idListino}")
    @Operation(description = "Tale API ripristina il listino scelto tramite idListino.", summary = "Ripristina listino non attivo.")
    public ResponseEntity<Message> ripristinaListino(@PathVariable("idListino") Long idListino) {
        gestioneListinoService.ripristinaListino(idListino);
        return new ResponseEntity<>(new Message("Listino ripristinato con successo!", HttpStatus.OK.value(), LocalDateTime.now().toString()), HttpStatus.OK);
    }

    @DeleteMapping("/tipologia-mutuo/{idMutuo}")
    @Operation(description = "Tale API elimina dal listino il mutuo scelto tramite idMuto. N.B Il mutuo classico non può essere eliminato.", summary = "Delete mutuo da listino.")
    public ResponseEntity<Message> deleteTipologiaMutuo(@PathVariable("idMutuo") Long idMutuo) {
        return gestioneListinoService.deleteTipologiaMutuo(idMutuo);
    }

    @PutMapping("/tipologia-mutuo/{idMutuo}")
    @Operation(description = "Tale API abilita o disabilita, in base allo stato corrente, il mutuo scelto tramite idMutuo.", summary = "Abilita/Disabilita mutuo in listino.")
    public ResponseEntity<Message> activeOrDisableTipologiaMutuo(@PathVariable("idMutuo") Long idMutuo) {
        return gestioneListinoService.activeOrDisableTipologiaMutuo(idMutuo);
    }

    @GetMapping("/parametri")
    @Operation(description = "Tale API restituisce i parametri dedicati ai tassi variabili.", summary = "Get parametri EURIBOR & IRS.")
    public ResponseEntity<List<ParametroResponse>> getParametri() {
        return ResponseEntity.ok(gestioneListinoService.getParametri());
    }
}