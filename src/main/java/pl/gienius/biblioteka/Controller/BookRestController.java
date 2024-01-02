package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gienius.biblioteka.Entity.Book;
import pl.gienius.biblioteka.Entity.Writer;
import pl.gienius.biblioteka.Service.BookService;

import java.util.List;

@RestController()
@RequestMapping(value = "/api")
public class BookRestController {

    Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /*@GetMapping()
    public String index(){
        logger.info("Log from index");
        return "Hello";
    }*/

    @GetMapping("/init")
    public String init(){
        logger.info("Init from REST");
        int flag = bookService.init();
        if(flag!=0) return "Database has been already initialized...";
        return "Successfully initialized...";
    }
    @GetMapping("/getLastBook")
    public ResponseEntity<Book> getLastBook(){
        Book book = bookService.getLastBook();
        if (book != null) {
            logger.info("Get book: " + book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            logger.info("Did not found any books in the database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            logger.info("Get book: " + book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            logger.info("Did not found the book by id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /*@PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody Book newBook) {
        bookService.addBook(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }*/




    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getWriterBooks(@RequestHeader("Writer-ID") Long writerId){
        List<Book> books = bookService.getWriterBooks(writerId);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }




}
