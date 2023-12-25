package pl.gienius.biblioteka.Service;

import org.springframework.stereotype.Service;
import pl.gienius.biblioteka.Entity.Reader;
import pl.gienius.biblioteka.Entity.Writer;
import pl.gienius.biblioteka.Repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    private ReaderRepository readerRepository;

    private List<Reader> readerList;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public List<Reader> getReaderList() {
        setReaderList();
        return readerList;
    }

    public void setReaderList() {
        readerList = (List<Reader>) readerRepository.findAll();
    }


    public Reader getReaderById(Long id) {
        setReaderList();
        if (id != null) {
            for (Reader r : readerList) {
                if (r.getId().equals(id)) {
                    return r;
                }
            }
        }
        return null;
    }
}
