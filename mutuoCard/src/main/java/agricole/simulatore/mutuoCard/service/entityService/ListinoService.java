package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.model.Listino;
import agricole.simulatore.mutuoCard.repository.ListinoRepository;
import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListinoService extends AbstractService<Listino, ListinoRepository> {

    public Listino findByIsActiveIsTrue() {
        return repository.findByIsActiveIsTrue().orElseThrow(() -> new ResourceNotFoundException("Nessun Listino Attivo!"));
    }

    public List<Listino> getLast10InactiveListini() {
        return repository.findTop10IsActiveIsFalseOrderedByCreatedDate(PageRequest.of(0, 10)).orElseThrow(() -> new ResourceNotFoundException("Nessun Listino Pregresso!"));
    }
}