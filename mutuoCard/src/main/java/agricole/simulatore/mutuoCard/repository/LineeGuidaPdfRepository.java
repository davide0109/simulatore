package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.LineeGuidaPdf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineeGuidaPdfRepository extends JpaRepository<LineeGuidaPdf, Long> {

}