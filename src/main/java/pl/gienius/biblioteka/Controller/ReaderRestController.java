package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gienius.biblioteka.Entity.Reader;
import pl.gienius.biblioteka.Entity.Writer;
import pl.gienius.biblioteka.Service.ReaderService;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/readers")
public class ReaderRestController {

    private ReaderService readerService;

    Logger logger = LoggerFactory.getLogger(ReaderRestController.class);

    public ReaderRestController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        List<Reader> readers = readerService.getReaderList();
        if (readers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(readers, HttpStatus.OK);
    }

    @PostMapping("/addReader")
    public ResponseEntity<Reader> addNewReader(@RequestBody Reader reader) {
        logger.info("Adding new writer: " + reader);
        if (reader == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reader savedReader = readerService.addReader(reader); // Save the new reader
        return new ResponseEntity<>(savedReader, HttpStatus.CREATED);
    }
}
