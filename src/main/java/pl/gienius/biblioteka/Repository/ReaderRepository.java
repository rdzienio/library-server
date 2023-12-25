package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import pl.gienius.biblioteka.Entity.Reader;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
