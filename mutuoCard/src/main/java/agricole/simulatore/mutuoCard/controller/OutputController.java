package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.dto.request.OutputRequest;
import agricole.simulatore.mutuoCard.service.GestioneSimulazioneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/output")
@Tag(name = "Output Controller", description = "Api per la gestione dell'output.")
public class OutputController {

    @Autowired
    private GestioneSimulazioneService gestioneSimulazioneService;

    @PostMapping("/")
    @Operation(description = "Tale API invia a TM l'output scelto in fase di simulazione a frontend.", summary = "Send output.")
    public void sendOutput(@RequestBody @Valid OutputRequest request) {
        gestioneSimulazioneService.sendOutput(request);
    }
}