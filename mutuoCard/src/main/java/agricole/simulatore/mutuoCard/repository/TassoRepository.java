package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.Tasso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TassoRepository extends JpaRepository<Tasso, Long> {

}