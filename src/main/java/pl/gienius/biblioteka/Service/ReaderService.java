package pl.gienius.biblioteka.Service;

import org.springframework.stereotype.Service;
import pl.gienius.biblioteka.Entity.Reader;
import pl.gienius.biblioteka.Repository.ReaderRepository;

import java.util.List;

@Service
public class ReaderService {

    private ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public List<Reader> getReaderList() {
        return (List<Reader>) readerRepository.findAll();
    }
}
