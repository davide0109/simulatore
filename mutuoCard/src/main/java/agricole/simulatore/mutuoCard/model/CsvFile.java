package agricole.simulatore.mutuoCard.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CSV_FILE")
public class CsvFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] fileData;

    private String contentType;

    private String fileName;
}