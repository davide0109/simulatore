package agricole.simulatore.mutuoCard.service;

import agricole.simulatore.mutuoCard.dto.shared.File;
import agricole.simulatore.mutuoCard.model.LineeGuidaPdf;
import agricole.simulatore.mutuoCard.model.Mutuo;
import agricole.simulatore.mutuoCard.service.entityService.LineeGuidaPdfService;
import agricole.simulatore.mutuoCard.service.entityService.MutuoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class PDFService {

    @Autowired
    private MutuoService mutuoService;

    @Autowired
    private LineeGuidaPdfService lineeGuidaPdfService;

    public void uploadPDF(Long idMutuo, MultipartFile file) throws IOException {
        if (Objects.isNull(idMutuo)) {
            lineeGuidaPdfService.create(new LineeGuidaPdf(file));
        } else {
            Mutuo mutuo = mutuoService.read(idMutuo);
            mutuo.setFilePdf(file.getBytes());
            mutuo.setContentType(file.getContentType());
            mutuo.setNomeFile(file.getOriginalFilename());
            mutuoService.update(mutuo);
        }
    }

    public File downloadPDF(Long idMutuo) {
        if (Objects.isNull(idMutuo)) return new File(lineeGuidaPdfService.read(1L));
        else return new File(mutuoService.read(idMutuo));
    }
}