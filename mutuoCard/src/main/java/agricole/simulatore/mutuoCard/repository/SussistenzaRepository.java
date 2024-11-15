package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.Sussistenza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface SussistenzaRepository extends JpaRepository<Sussistenza, Long> {

    Optional<Sussistenza> findByProvinciaIgnoreCase(String provincia);
}