package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.VistaParametri;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VistaParametriRepository extends JpaRepository<VistaParametri, Long> {

    List<VistaParametri> findByCodTassoIn(List<String> codTasso);
}