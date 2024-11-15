package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.model.Listino;
import agricole.simulatore.mutuoCard.model.Mutuo;
import agricole.simulatore.mutuoCard.repository.MutuoRepository;

import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MutuoService extends AbstractService<Mutuo, MutuoRepository> {

    public void deleteByListinoIsNull() {
        repository.deleteByListinoIsNull();
    }

    public List<Mutuo> findByListino(Listino listino) {
        return repository.findByListino(listino).orElseThrow(() -> new ResourceNotFoundException("Nessuna tipologia mutuo associata a tale listino: " + listino.getNome()));
    }
}