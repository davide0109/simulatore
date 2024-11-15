package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.dto.response.ProvinciaResponse;
import agricole.simulatore.mutuoCard.dto.response.SimulazioneResponse;
import agricole.simulatore.mutuoCard.service.GestioneSimulazioneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestione-simulazione")
@Tag(name = "Simulazione Controller", description = "Api per la gestione della simulazione.")
public class GestioneSimulazioneController {

    @Autowired
    private GestioneSimulazioneService gestioneSimulazioneService;

    @GetMapping("/province")
    @Operation(description = "Tale API restituisce una lista con tutte le province italiane.", summary = "Get province italiane.")
    public ResponseEntity<List<ProvinciaResponse>> getProvince() {
        return ResponseEntity.ok(gestioneSimulazioneService.getProvince());
    }

    @GetMapping("/sussistenza")
    @Operation(description = "Tale API restituisce la sussistenza corretta in base alla provincia scelta e al numero di componenti del nucleo familiare.", summary = "Get sussistenza.")
    public ResponseEntity<Integer> getSussistenza(@RequestParam(name = "provincia") String provincia, @RequestParam(name = "numeroComponenti") Integer numeroComponenti) {
        return ResponseEntity.ok(gestioneSimulazioneService.getSussistenza(provincia, numeroComponenti));
    }

    @PreAuthorize("hasAuthority('SIMULATORE_GESTORE') || hasAuthority('SIMULATORE_ADMIN')")
    @GetMapping("/init")
    @Operation(description = "Tale API restituisce, se esistente, la simulazione associata all'Id Preventivo inviato all interno del token e i dati dell utente che esegue la chiamata.", summary = "Get simulazione.")
    public ResponseEntity<SimulazioneResponse> getSimulazione() {
        return ResponseEntity.ok(gestioneSimulazioneService.getSimulazione());
    }
}