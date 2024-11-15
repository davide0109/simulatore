package agricole.simulatore.mutuoCard.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "V_TASSI_SIMULATORE")
public class VistaParametri {

    @Id
    @Column(nullable = false, name = "COD_TASSO")
    private String codTasso;

    @Column(nullable = false, name = "VALUE_TASSO")
    private String valueTasso;

    @Column(name = "DESC_TASSO")
    private String descTasso;

    @Column(name = "DT_INIZIO_VALIDITA")
    private LocalDate dtInizioValidita;

    @Column(name = "DT_FINE_VALIDITA")
    private LocalDate dtFineValidita;

    private String istituto;
}