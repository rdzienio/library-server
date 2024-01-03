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
import java.util.Optional;

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
        return (List<Rental>) rentalRepository.findByEndRentIsNull();
    }

    public List<Rental> getActiveRentals() {
        return (List<Rental>) rentalRepository.findByEndRentIsNull();
    }

    public List<Rental> getActiveRentalsByBook(Long bookId){
        return (List<Rental>) rentalRepository.findByBookIdAndEndRentIsNull(bookId);
    }

    public boolean rentBook(Long bookId, Long readerId) {
        Book toRent = bookService.getBookById(bookId);
        Reader reader = readerService.getReaderById(readerId);
        if (toRent != null && reader != null) {
            if (bookService.canBeRented(bookId)) {
                LocalDate startDate = LocalDate.now();
                logger.info("New rental: " + rentalRepository.save(new Rental(startDate, reader, toRent)));
                bookService.rentBook(bookId);
                return true;
            } else {
                logger.info("This book: " + bookId + " cannot be rented!");
                return false;
            }
        }
        return false;
    }

    public boolean returnBook(Long bookId, Long readerId) {
        Rental rental = rentalRepository.findActiveRentalByBookIdAndReaderId(bookId, readerId);
        if (rental != null) {
            rental.setEndRent(LocalDate.now());
            rentalRepository.save(rental);
            logger.info("Returned the book: " + bookId + " by reader: " + readerId);
            if (isRented(bookId)) {
                logger.info("Book " + bookId + " is not rented already");
                bookService.returnBook(bookId);
            }
            return true;
        } else {
            logger.info("Could not find the rental for: " + bookId + " by reader: " + readerId);
            return false;
        }
    }

    public boolean isRented(Long bookId) {
        logger.info("Rented list: " + getActiveRentalsByBook(bookId).size());
        return !getActiveRentalsByBook(bookId).isEmpty();
    }

    public void removeRentalByBook(Long bookId) {
         rentalRepository.deleteByBookId(bookId);
    }


}
