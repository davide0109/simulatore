package agricole.simulatore.mutuoCard.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TmOutputTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String request;

    @Lob
    private String response;

    private Integer statusCode;

    private LocalDateTime requestDate;

    private LocalDateTime responseDate;

    public TmOutputTransaction(String request, LocalDateTime requestDate) {
        this.request = request;
        this.requestDate = requestDate;
    }
}