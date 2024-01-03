package pl.gienius.biblioteka.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.gienius.biblioteka.Entity.Rental;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findByEndRentIsNull();

    Rental findActiveRentalByBookIdAndReaderId(Long bookId, Long readerId);

    List<Rental> findActiveRentalByBookId(Long bookId);

    List<Rental> findByBookIdAndEndRentIsNull(Long bookId);

    @Transactional
    void deleteByBookId(Long bookId);
}
