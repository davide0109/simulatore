package agricole.simulatore.mutuoCard.dto.shared;

import agricole.simulatore.mutuoCard.dto.GaranteDTO;
import agricole.simulatore.mutuoCard.dto.IntestatarioDTO;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutuoCardSoggettoSimulatore {

    private String codiceFiscale;

    @NotBlank(message = "Errore: Cognome garante mancante.")
    private String cognome;

    @NotBlank(message = "Errore: Nome garante mancante.")
    private String nome;

    private Integer ndg;

    private Double altriRedditi = 0.0;

    private Double impegniFinanziari = 0.0;

    private Double oneriDaAlimenti = 0.0;

    private Double redditiDaLavoro = 0.0;

    public MutuoCardSoggettoSimulatore(GaranteDTO garante) {
        this.codiceFiscale = garante.getCodiceFiscale();
        this.cognome = garante.getCognome();
        this.nome = garante.getNome();
        this.ndg = garante.getNdg();
        this.altriRedditi = garante.getAltriRedditi();
        this.impegniFinanziari = garante.getImpegniFinanziari();
        this.oneriDaAlimenti = garante.getOneriDaAlimenti();
        this.redditiDaLavoro = garante.getRedditiDaLavoro();
    }

    public MutuoCardSoggettoSimulatore(IntestatarioDTO intestatario) {
        this.codiceFiscale = intestatario.getCodiceFiscale();
        this.cognome = intestatario.getCognome();
        this.nome = intestatario.getNome();
        this.ndg = intestatario.getNdg();
        this.altriRedditi = intestatario.getAltriRedditi();
        this.impegniFinanziari = intestatario.getImpegniFinanziari();
        this.oneriDaAlimenti = intestatario.getOneriDaAlimenti();
        this.redditiDaLavoro = intestatario.getRedditiDaLavoro();
    }
}