package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.gienius.biblioteka.Entity.Writer;

@Repository
public interface WriterRepository extends CrudRepository<Writer,Long> {
}
