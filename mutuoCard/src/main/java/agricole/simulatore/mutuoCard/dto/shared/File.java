package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.model.LineeGuidaPdf;
import agricole.simulatore.mutuoCard.model.Mutuo;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private byte[] file;

    private String nomeFile;

    private String contentType;

    public File(Mutuo mutuo) {
        this.file = Objects.nonNull(mutuo.getFilePdf()) ? mutuo.getFilePdf() : null;
        this.nomeFile = Objects.nonNull(mutuo.getNomeFile()) ? mutuo.getNomeFile() : "Pdf_" + mutuo.getNome() + ".pdf";
        this.contentType = Objects.nonNull(mutuo.getContentType()) ? mutuo.getContentType() : null;
    }

    public File(LineeGuidaPdf guidaPdf) {
        this.file = Objects.nonNull(guidaPdf.getFilePdf()) ? guidaPdf.getFilePdf() : null;
        this.nomeFile = Objects.nonNull(guidaPdf.getNomeFile()) ? guidaPdf.getNomeFile() : "Pdf_linee_guida.pdf";
        this.contentType = Objects.nonNull(guidaPdf.getContentType()) ? guidaPdf.getContentType() : null;
    }
}