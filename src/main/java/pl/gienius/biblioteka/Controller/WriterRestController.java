package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gienius.biblioteka.Entity.Book;
import pl.gienius.biblioteka.Entity.Writer;
import pl.gienius.biblioteka.Service.BookService;
import pl.gienius.biblioteka.Service.WriterService;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping(value = "/api/writers")
public class WriterRestController {

    Logger logger = LoggerFactory.getLogger(WriterRestController.class);

    private WriterService writerService;
    private BookService bookService;

    public WriterRestController(WriterService writerService, BookService bookService) {
        this.writerService = writerService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Writer>> getAllWriter() {
        List<Writer> writers = writerService.getAllWriters();
        if (writers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(writers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Writer> getWriterById(@PathVariable Long id) {
        Writer writer = writerService.getWriterById(id);
        if (writer != null) {
            logger.info("Get writer: " + writer);
            return new ResponseEntity<>(writer, HttpStatus.OK);
        } else {
            logger.info("Did not found the writer by id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addNewBook(@RequestHeader("Writer-ID") Long writerId, @RequestBody Book newBook) {
        logger.info("Writer: " + writerId + ", Adding new book: " + newBook);
        Writer writer = writerService.getWriterById(writerId);
        if (writer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        newBook.setWriter(writer); // Set the writer for the book
        Book savedBook = bookService.addBook(newBook); // Save the new book
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestHeader("Writer-ID") Long writerId, @RequestBody Book book) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            if (!Objects.equals(existingBook.getWriter().getId(), writerId)) {
                logger.info("Writer: " + writerId + " is not authorized to edit this book: " + id);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(bookService.blockBook(existingBook.getId())!=0) {
                logger.info("Updating book: " + book);
                book.setId(existingBook.getId());
                book.setWriter(existingBook.getWriter());
                bookService.updateBook(id, book);
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
            else {
                logger.info("The book with ID: " + id + " is rented by somebody! Try again later...");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            logger.info("Did not found the book by id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable Long id, @RequestHeader("Writer-ID") Long writerId) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            if (!Objects.equals(existingBook.getWriter().getId(), writerId)) {
                logger.info("Writer: " + writerId + " is not authorized to edit this book: " + id);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            logger.info("Removing book: " + existingBook);
            bookService.removeBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info("Did not found the book by id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
