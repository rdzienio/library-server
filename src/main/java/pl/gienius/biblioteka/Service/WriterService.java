package pl.gienius.biblioteka.Service;

import org.springframework.stereotype.Service;
import pl.gienius.biblioteka.Entity.Writer;
import pl.gienius.biblioteka.Repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    List<Writer> allWriters = new ArrayList<Writer>();

    public void setAllWriters(){
        allWriters = (List<Writer>) writerRepository.findAll();
    }

    public List<Writer> getAllWriters(){
        setAllWriters();
        return allWriters;
    }

    public Writer addWriter(Writer newWriter){
        return writerRepository.save(newWriter);
    }

    public Writer getWriterByName(String name){
        setAllWriters();
        if (name != null) {
            for (Writer w : allWriters) {
                if (w.getName().equals(name)) {
                    return w;
                }
            }
        }
        return null;
    }

    public Writer getWriterById(Long id){
        setAllWriters();
        if (id != null) {
            for (Writer w : allWriters) {
                if (w.getId().equals(id)) {
                    return w;
                }
            }
        }
        return null;
    }

    public void updateWriter(Long id, Writer updatedWriter) {
        Optional<Writer> writerOptional = writerRepository.findById(id);
        if (writerOptional.isPresent()) {
            Writer writerToUpdate = writerOptional.get();
            writerToUpdate.setName(updatedWriter.getName());
            writerRepository.save(writerToUpdate);
        }
    }

    public void removeWriter(Long id) {
        writerRepository.deleteById(id);
    }

}
