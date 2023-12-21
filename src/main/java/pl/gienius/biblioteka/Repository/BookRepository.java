package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.gienius.biblioteka.Entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
