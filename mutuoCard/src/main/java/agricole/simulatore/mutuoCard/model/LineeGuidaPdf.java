package agricole.simulatore.mutuoCard.model;

import javax.persistence.*;

import lombok.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LINEE_GUIDA_PDF")
public class LineeGuidaPdf {

    @Id
    private Long id;

    @Lob
    @Column(name = "FILE_PDF")
    private byte[] filePdf;

    @Column(name = "NOME_FILE")
    private String nomeFile;


    @Column(name = "CONTENT_TYPE")
    private String contentType;

    public LineeGuidaPdf(MultipartFile file) throws IOException {
        this.id = 1L;
        this.filePdf = file.getBytes();
        this.nomeFile = file.getOriginalFilename();
        this.contentType = file.getContentType();
    }
}