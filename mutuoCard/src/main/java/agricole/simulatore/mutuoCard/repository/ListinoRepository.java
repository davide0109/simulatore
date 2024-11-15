package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.Listino;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListinoRepository extends JpaRepository<Listino, Long> {

    Optional<Listino> findByIsActiveIsTrue();

    @Query("SELECT l FROM Listino l WHERE l.isActive = false ORDER BY l.createdDate DESC")
    Optional<List<Listino>> findTop10IsActiveIsFalseOrderedByCreatedDate(Pageable pageable);
}