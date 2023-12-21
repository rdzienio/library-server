package pl.gienius.biblioteka.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Writer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Writer() {
    }

    public Writer(String name) {
        this.name = name;
    }

    //@OneToMany(mappedBy = "writer")
    //private List<Book> books;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }*/

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

