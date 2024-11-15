package agricole.simulatore.mutuoCard.service.entityService;

import agricole.simulatore.mutuoCard.utils.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<Entity, Repository extends JpaRepository<Entity, Long>> {

    @Autowired
    protected Repository repository;

    public Entity create(Entity entity) {
        return this.repository.save(entity);
    }

    public List<Entity> find(Example<Entity> spec) {
        return this.repository.findAll(Example.of(spec.getProbe()));
    }

    public List<Entity> findAll() {
        return this.repository.findAll();
    }

    public long count() {
        return this.repository.count();
    }

    public void delete(Long id) throws ResourceNotFoundException {
        this.delete(this.read(id));
    }

    public void delete(Entity entity) throws ResourceNotFoundException {
        this.repository.delete(entity);
    }

    public Entity read(Long id) throws ResourceNotFoundException {
        Optional<Entity> item = this.repository.findById(id);
        return item.orElseThrow(() -> new ResourceNotFoundException("Resource with id '" + id + "' not found"));
    }

    public Entity update(Entity entity) throws ResourceNotFoundException {
        return this.repository.save(entity);
    }
}