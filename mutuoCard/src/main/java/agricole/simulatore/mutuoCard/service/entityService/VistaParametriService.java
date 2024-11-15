package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.dto.response.ParametroResponse;
import agricole.simulatore.mutuoCard.model.VistaParametri;
import agricole.simulatore.mutuoCard.repository.VistaParametriRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VistaParametriService extends AbstractService<VistaParametri, VistaParametriRepository> {

    public List<ParametroResponse> getParametri(List<String> codiciTasso) {
        List<VistaParametri> parametri = repository.findByCodTassoIn(codiciTasso);
        return parametri.stream().map(ParametroResponse::new).collect(Collectors.toList());
    }
}