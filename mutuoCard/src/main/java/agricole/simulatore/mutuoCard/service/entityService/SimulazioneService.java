package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.model.Simulazione;
import agricole.simulatore.mutuoCard.repository.SimulazioneRepository;

import org.springframework.stereotype.Service;

@Service
public class SimulazioneService extends AbstractService<Simulazione, SimulazioneRepository> {

    public Simulazione findByIdPreventivo(String idPreventivo) {
        return repository.findByIdPreventivo(idPreventivo).orElse(null);
    }

    public Boolean existByIdPreventivo(String idPreventivo) {
        return repository.existsByIdPreventivo(idPreventivo);
    }
}