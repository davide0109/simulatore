package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.dto.shared.File;
import agricole.simulatore.mutuoCard.service.PDFService;
import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;
import agricole.simulatore.mutuoCard.utils.messaging.Message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/pdf")
@Tag(name = "PDF Controller", description = "Api per la gestione dei file PDF per le tipologie mutuo.")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @Operation(description = "Api per il caricamento del file PDF del mutuo scelto tramite idMutuo.", summary = "Carica pdf mutuo")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> uploadPDF(@RequestParam(value = "idMutuo", required = false) Long idMutuo, @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new Message("File Pdf vuoto!", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
        }
        pdfService.uploadPDF(idMutuo, file);
        return new ResponseEntity<>(new Message("Pdf caricato con successo!", HttpStatus.OK.value(), LocalDateTime.now().toString()), HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    @Operation(description = "Api per il download del file PDF del mutuo scelto tramite idMutuo.", summary = "Scarica pdf mutuo")
    public ResponseEntity<byte[]> downloadPDF(@RequestParam(value = "idMutuo", required = false) Long idMutuo) {
        File file = pdfService.downloadPDF(idMutuo);
        if (Objects.nonNull(file.getFile())) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            headers.setContentDispositionFormData("attachment", file.getNomeFile());
            return ResponseEntity.ok().headers(headers).body(file.getFile());
        } else {
            throw new ResourceNotFoundException("File non trovato per il mutuo scelto.");
        }
    }
}