package agricole.simulatore.mutuoCard.model;

import agricole.simulatore.mutuoCard.dto.enums.DominiConsapEnum;
import agricole.simulatore.mutuoCard.dto.enums.TipologiaTassoEnum;
import agricole.simulatore.mutuoCard.dto.shared.File;

import javax.persistence.*;

import lombok.*;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mutuo extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private DominiConsapEnum garanzia;

    private Boolean isClassic;

    private Boolean isActive;

    private Boolean isDeleted;

    @Lob
    private byte[] filePdf;

    private String nomeFile;

    private String contentType;

    @OneToOne
    private Tasso fissoEsplicito;

    @OneToOne
    private Tasso fissoParametrato;

    @OneToOne
    private Tasso variabile;

    @OneToOne
    private Tasso variabileCap;

    @OneToOne
    private Costante costanti;

    @ManyToOne
    private Listino listino;

    public Mutuo(String nome, File file, List<Tasso> tassi, Costante costanti, Boolean isClassic, Boolean isActive, DominiConsapEnum garanzia) {
        this.nome = nome;
        this.isClassic = isClassic;
        this.isActive = isActive;
        this.isDeleted = Boolean.FALSE;
        this.filePdf = Objects.nonNull(file) ? file.getFile() : null;
        this.nomeFile = Objects.nonNull(file) ? file.getNomeFile() : null;
        this.contentType = Objects.nonNull(file) ? file.getContentType() : null;
        this.fissoEsplicito = tassi.stream().filter(tasso -> tasso.getTipologiaTasso().equals(TipologiaTassoEnum.FISSO_ESPLICITO)).findFirst().orElse(null);
        this.fissoParametrato = tassi.stream().filter(tasso -> tasso.getTipologiaTasso().equals(TipologiaTassoEnum.FISSO_PARAMETRICO)).findFirst().orElse(null);
        this.variabile = tassi.stream().filter(tasso -> tasso.getTipologiaTasso().equals(TipologiaTassoEnum.VARIABILE)).findFirst().orElse(null);
        this.variabileCap = tassi.stream().filter(tasso -> tasso.getTipologiaTasso().equals(TipologiaTassoEnum.VARIABILE_CAP)).findFirst().orElse(null);
        this.costanti = costanti;
        this.garanzia = garanzia;
    }
}