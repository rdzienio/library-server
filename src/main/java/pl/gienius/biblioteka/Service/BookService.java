package pl.gienius.biblioteka.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.gienius.biblioteka.Repository.BookRepository;
import pl.gienius.biblioteka.Entity.Book;
import pl.gienius.biblioteka.Entity.Writer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);
    private BookRepository bookRepository;

    private WriterService writerService;

    private Boolean initFlag = false;

    public BookService(BookRepository bookRepository, WriterService writerService) {
        this.bookRepository = bookRepository;
        this.writerService = writerService;
    }

    private List<Book> allBooks = new ArrayList<Book>();  //wszystkie ksiązki
    private List<Book> availableBooks = new ArrayList<Book>(); //dostępne ksiązki do wypożyczenia i blokowania
    private List<Book> rentedBooks = new ArrayList<Book>(); //wypożyczone ksiązki, nie mogą być obslugiwane przez pisarzy, ale mogą być czytane

    private List<Book> blockedBooks = new ArrayList<Book>(); //zablokowane książki przez pisarzy

    public int init() {
        if (initFlag) return -1;
        Writer w = new Writer("Sienkiewicz");
        writerService.addWriter(w);
        Book newBook = new Book("Ogniem i mieczem", w, LocalDate.parse("2015-11-13"), "Ogniem i mieczem – pierwsza z trzech powieści historycznych będących częścią Trylogii, pisanej dla pokrzepienia serc przez Henryka Sienkiewicza w latach 1884–1888.");
        addBook(newBook);
        Writer w1 = new Writer("Dolor");
        writerService.addWriter(w1);
        addBook(new Book("Lorem Ipsum", w1, LocalDate.parse("2000-10-01"), "Ala ma kont Vol 89"));
        initFlag = true;
        return 0;
    }


    public List<Book> getAllBooks() {
        setAllBooks();
        if (!allBooks.isEmpty())
            logger.info("ostatnie Id to: " + allBooks.getLast().getId());
        else
            logger.info("Lista jest pusta!");
        return allBooks;
    }

    public void setAllBooks() {
        allBooks = (List<Book>) bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public List<Book> getBlookList() {
        return blockedBooks;
    }

    public Book addBook(Book book) {
        logger.info("BookService#Adding: " + book);
        availableBooks.add(book);
        return bookRepository.save(book);
    }

    public Book getLastBook() {
        setAllBooks();
        if (!allBooks.isEmpty())
            return allBooks.getLast();
        else
            return null;
    }

    public Book getBookById(Long id) {
        setAllBooks();
        if (id != null) {
            for (Book book : allBooks) {
                if (book.getId().equals(id)) {
                    return book;
                }
            }
        }
        return null;
    }

    public void updateBook(Long id, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setId(updatedBook.getId());
            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setDescription(updatedBook.getDescription());
            bookToUpdate.setReleaseDate(updatedBook.getReleaseDate());
            bookToUpdate.setWriter(updatedBook.getWriter());
            bookRepository.save(bookToUpdate);
        }
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean readyToUpdate(Long id) {
        Book toBlock = getBookById(id);
        if (toBlock != null) {
            return !rentedBooks.contains(toBlock);
        }
        return false;
    }

    public int blockBook(Long id) {
        if (getBookById(id) != null) {
            blockedBooks.add(getBookById(id));
            if (availableBooks.remove(getBookById(id))) logger.info("Book deleted");
            logger.info("Available books: " + availableBooks.size());
            logger.info("Blocked books: " + blockedBooks.size());
            return 0;
        } else
            return -1;
    }

    public int unblockBook(Long id) {
        if (getBookById(id) != null) {
            if (blockedBooks.remove(getBookById(id))) logger.info("Book deleted");
            availableBooks.add(getBookById(id));
            logger.info("Available books: " + availableBooks.size());
            logger.info("Blocked books: " + blockedBooks.size());
            return 0;
        } else
            return -1;
    }

    public Book getBookByTitle(String title) {
        setAllBooks();
        if (title != null) {
            for (Book book : allBooks) {
                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }
        return null;
    }

    public Book getBookByWriter(String writer) {
        setAllBooks();
        if (writer != null) {
            for (Book book : allBooks) {
                if (book.getWriter().getName().equals(writer)) {
                    return book;
                }
            }
        }
        return null;
    }

    public boolean canBeRented(Long id) {
        Book toBlock = getBookById(id);
        if (toBlock != null) {
            return !blockedBooks.contains(toBlock);
        }
        return false;
    }

    public void rentBook(Long id) {
        if (getBookById(id) != null) {
            if (!rentedBooks.contains(getBookById(id)))
                rentedBooks.add(getBookById(id));
        }
    }

    public void returnBook(Long id) {
        if (getBookById(id) != null) {
            if (rentedBooks.contains(getBookById(id)))
                rentedBooks.remove(getBookById(id));
        }
    }
}
