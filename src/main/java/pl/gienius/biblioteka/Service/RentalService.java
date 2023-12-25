package pl.gienius.biblioteka.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.gienius.biblioteka.Entity.Book;
import pl.gienius.biblioteka.Entity.Reader;
import pl.gienius.biblioteka.Entity.Rental;
import pl.gienius.biblioteka.Repository.RentalRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RentalService {

    private RentalRepository rentalRepository;
    private BookService bookService;
    private ReaderService readerService;

    Logger logger = LoggerFactory.getLogger(RentalService.class);

    public RentalService(RentalRepository rentalRepository, BookService bookService, ReaderService readerService) {
        this.rentalRepository = rentalRepository;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    public List<Rental> getRentalList() {
        return (List<Rental>) rentalRepository.findAll();
    }

    public List<Rental> getActiveRentals(){
        return (List<Rental>) rentalRepository.findByEndRentIsNull();
    }

    public boolean rentBook(Long bookId, Long readerId){
        Book toRent = bookService.getBookById(bookId);
        Reader reader = readerService.getReaderById(readerId);
        if (toRent != null && reader != null) {
            if(bookService.canBeRented(bookId)) {
                LocalDate startDate = LocalDate.now();
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                logger.info("New rental: " + rentalRepository.save(new Rental(startDate, reader, toRent)));
                bookService.rentBook(bookId);
                return true;
            }
            else {
                logger.info("This book: " + bookId + " cannot be rented!");
                return false;
            }
        }
        return false;
    }
}
