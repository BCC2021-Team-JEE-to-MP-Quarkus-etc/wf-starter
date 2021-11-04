package com.baloise.codecamp.wildfly.books;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="book")
@NamedQuery(name = Book.FIND_ALL, query = "SELECT b FROM Book b")
@NamedQuery(name = Book.FIND_BY_TITLE, query = "SELECT b FROM Book b where b.title like ?1")
public class Book {
    public static final String FIND_ALL = "Book.ALL";
    public static final String FIND_BY_TITLE = "Book.BY_TITLE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
