package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import pl.gienius.biblioteka.Entity.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findByEndRentIsNull();

    Rental findActiveRentalByBookIdAndReaderId(Long bookId, Long readerId);

    List<Rental> findActiveRentalByBookId(Long bookId);
}
