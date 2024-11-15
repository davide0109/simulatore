package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.Listino;
import agricole.simulatore.mutuoCard.model.Mutuo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MutuoRepository extends JpaRepository<Mutuo, Long> {

    void deleteByListinoIsNull();

    Optional<List<Mutuo>> findByListino(Listino listino);
}