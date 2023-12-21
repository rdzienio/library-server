package pl.gienius.biblioteka.Entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private String title;

    //private String author;
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Writer writer; // Link to the Writer entity

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private String description;

    public Book() {
    }

    public Book(String title, Writer writer, LocalDate releaseDate, String description) {
        this.title = title;
        this.writer = writer;
        //this.author = author;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    public Book(Long id, String title, Writer writer, LocalDate releaseDate, String description) {
        this.title = title;
        this.writer = writer;
        //this.author = author;
        this.releaseDate = releaseDate;
        this.description = description;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //public String getAuthor() {
    //    return author;
    //}

    //public void setAuthor(String author) {
    //    this.author = author;
    //}

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writer=" + writer +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(writer, book.writer) && Objects.equals(getReleaseDate(), book.getReleaseDate()) && Objects.equals(getDescription(), book.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), writer, getReleaseDate(), getDescription());
    }
}
