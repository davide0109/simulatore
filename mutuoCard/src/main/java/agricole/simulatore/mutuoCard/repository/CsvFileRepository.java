package agricole.simulatore.mutuoCard.repository;

import agricole.simulatore.mutuoCard.model.CsvFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvFileRepository extends JpaRepository<CsvFile, Long> {

}