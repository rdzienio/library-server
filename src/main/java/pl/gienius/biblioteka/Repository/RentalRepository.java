package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import pl.gienius.biblioteka.Entity.Rental;

public interface RentalRepository extends CrudRepository<Rental, Long> {
}
