package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.gienius.biblioteka.Repository.BookRepository;
import pl.gienius.biblioteka.Service.BookService;

@Controller
public class BookController {

    private static final Logger Logger = LoggerFactory.getLogger(BookController.class);
    private BookRepository bookRepository;
    private BookService bookService;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        //bookService.init();
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("ksiazki", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/availableBooks")
    public String getAvailableBooks(Model model){
        model.addAttribute("dostepne", bookService.getAvailableBooks());
        return "availableBooks";
    }

    @GetMapping("/rentedBooks")
    public String getRentedBooks(Model model){
        model.addAttribute("wypozyczone", bookService.getRentedBooks());
        return "rentedBooks";
    }
}
