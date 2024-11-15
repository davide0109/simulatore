package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.service.entityService.SussistenzaService;

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

@RestController
@RequestMapping("/csv")
@Tag(name = "CSV Controller", description = "Api per la gestione del file CSV dedicato alla sussistenza.")
public class CSVController {

    @Autowired
    private SussistenzaService sussistenzaService;

    @Operation(description = "Api per il caricamento del file CSV dedicato alla sussistenza.", summary = "Carica sussistenza.csv")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCSV(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file.");
        }
        sussistenzaService.saveCSVData(file.getInputStream());
        sussistenzaService.saveCSVFile(file);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded and data saved successfully.");
    }

    @GetMapping(value = "/download")
    @Operation(description = "Api per il download del file CSV dedicato alla sussistenza.", summary = "Scarica sussistenza.csv")
    public ResponseEntity<byte[]> downloadCSV() {
        byte[] fileData = sussistenzaService.downloadCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "sussistenza.csv");
        return ResponseEntity.ok().headers(headers).body(fileData);
    }
}