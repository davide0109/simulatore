package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.model.CsvFile;
import agricole.simulatore.mutuoCard.model.Sussistenza;
import agricole.simulatore.mutuoCard.repository.CsvFileRepository;
import agricole.simulatore.mutuoCard.repository.SussistenzaRepository;
import agricole.simulatore.mutuoCard.utils.ApplicationConstant;
import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe di servizio per la gestione delle sussistenze.
 */
@Service
public class SussistenzaService extends AbstractService<Sussistenza, SussistenzaRepository> {

    @Autowired
    private ApplicationConstant applicationConstant;

    @Autowired
    private CsvFileRepository csvFileRepository;

    private static final Logger logger = LoggerFactory.getLogger(SussistenzaService.class);

    private static final CSVFormat csvFormat = CSVFormat.DEFAULT
        .builder()
        .setHeader()
        .setSkipHeaderRecord(true)
        .setIgnoreHeaderCase(true)
        .setTrim(true)
        .build();

    /**
     * Inizializza il servizio caricando i dati CSV nel database se non sono già presenti.
     *
     * @throws IOException se si verifica un errore durante il caricamento del file CSV.
     */
    @PostConstruct
    public void init() throws IOException {
        if (count() == 0)
            saveCSVData(new ClassPathResource(applicationConstant.getFilePath() + applicationConstant.getFileNameSussistenza()).getInputStream());
        if (csvFileRepository.count() == 0)
            saveCSVFile(new ClassPathResource(applicationConstant.getFilePath() + applicationConstant.getFileNameSussistenza()).getInputStream());
    }

    /**
     * Salva i dati CSV nel database.
     *
     * @param file InputStream del file CSV da elaborare.
     */
    public void saveCSVData(InputStream file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file))) {
            insertOrUpdate(new CSVParser(reader, csvFormat).stream().map(Sussistenza::new).collect(Collectors.toCollection(ArrayList::new)));
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage(), e);
            // Gestisci l'eccezione
        } catch (IllegalArgumentException e) {
            logger.error("Invalid zone value in CSV file: {}", e.getMessage(), e);
        }
    }

    /**
     * Salva un file CSV nel database.
     *
     * @param file il file CSV da salvare.
     */
    public void saveCSVFile(MultipartFile file) {
        csvFileRepository.findById(1L).map(csvFile -> {
            try {
                csvFile.setFileData(file.getBytes());
                return csvFileRepository.save(csvFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElseGet(() -> {
            try {
                return csvFileRepository.save(new CsvFile(null, file.getBytes(), "text/csv", "sussistenza.csv"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Salva un file CSV dal flusso di input nel database.
     *
     * @param file il flusso di input del file CSV da salvare.
     */
    public void saveCSVFile(InputStream file) {
        try {
            csvFileRepository.save(new CsvFile(null, readAllBytes(file), "text/csv", "sussistenza.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024]; // Buffer temporaneo
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray(); // Restituisce l'array di byte
        }
    }

    /**
     * Scarica il file CSV dal database.
     *
     * @return i dati del file CSV come array di byte.
     */
    public byte[] downloadCSV() {
        return csvFileRepository.findById(1L)
            .orElseThrow(() -> new ResourceNotFoundException("File non trovato!"))
            .getFileData();
    }

    /**
     * Inserisce o aggiorna le sussistenze nel database.
     *
     * @param sussistenze la lista di sussistenze da inserire o aggiornare.
     */
    public void insertOrUpdate(List<Sussistenza> sussistenze) {
        List<Sussistenza> sussistenzeDb = repository.findAll();
        sussistenze.forEach(sussistenza -> sussistenzeDb.stream()
            .filter(existingSussistenza -> existingSussistenza.getProvincia().equalsIgnoreCase(sussistenza.getProvincia()))
            .findFirst()
            .map(existingSussistenza -> {
                existingSussistenza.setRegione(sussistenza.getRegione());
                existingSussistenza.setZona(sussistenza.getZona());
                existingSussistenza.setSigla(sussistenza.getSigla());
                existingSussistenza.setNumeroFamiliari_1(sussistenza.getNumeroFamiliari_1());
                existingSussistenza.setNumeroFamiliari_2(sussistenza.getNumeroFamiliari_2());
                existingSussistenza.setNumeroFamiliari_3(sussistenza.getNumeroFamiliari_3());
                existingSussistenza.setNumeroFamiliari_4(sussistenza.getNumeroFamiliari_4());
                existingSussistenza.setNumeroFamiliari_5(sussistenza.getNumeroFamiliari_5());
                existingSussistenza.setNumeroFamiliari_6(sussistenza.getNumeroFamiliari_6());
                existingSussistenza.setNumeroFamiliari_7(sussistenza.getNumeroFamiliari_7());
                existingSussistenza.setNumeroFamiliari_8(sussistenza.getNumeroFamiliari_8());
                existingSussistenza.setNumeroFamiliari_9(sussistenza.getNumeroFamiliari_9());
                existingSussistenza.setNumeroFamiliari_10(sussistenza.getNumeroFamiliari_10());
                return update(existingSussistenza);
            })
            .orElseGet(() -> create(sussistenza)));

        repository.deleteAll(sussistenzeDb.stream()
            .filter(existing -> sussistenze.stream()
                .noneMatch(s -> s.getProvincia().equalsIgnoreCase(existing.getProvincia()))
            )
            .collect(Collectors.toList()));
    }

    /**
     * Trova una sussistenza in base alla provincia.
     *
     * @param provincia la provincia da cercare.
     * @return la sussistenza corrispondente.
     * @throws ResourceNotFoundException se non viene trovata alcuna sussistenza per la provincia specificata.
     */
    public Sussistenza findByProvincia(String provincia) {
        return repository.findByProvinciaIgnoreCase(provincia)
            .orElseThrow(() -> new ResourceNotFoundException("Risorsa non trovata! - Provincia non presente: " + provincia));
    }
}
