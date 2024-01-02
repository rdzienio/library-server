package pl.gienius.biblioteka.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.gienius.biblioteka.Entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findBookByWriterId(Long writerId);
}
