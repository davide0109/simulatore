package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.Simulazione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimulazioneRepository extends JpaRepository<Simulazione, Long> {

    Optional<Simulazione> findByIdPreventivo(String idPreventivo);
}