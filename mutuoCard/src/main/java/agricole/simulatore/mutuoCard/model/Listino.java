package agricole.simulatore.mutuoCard.model;

import javax.persistence.*;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listino extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name="IS_ACTIVE")
    private Boolean isActive;

    public Listino(Boolean isActive, String nome) {
        this.isActive = isActive;
        this.nome = nome;
    }
}