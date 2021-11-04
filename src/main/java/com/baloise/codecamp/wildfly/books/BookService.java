package com.baloise.codecamp.wildfly.books;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BookService {

    @PersistenceContext(unitName = "quickstarterDS")
    private EntityManager em;

    public List<Book> list() {
        return em.createNamedQuery(Book.FIND_ALL).getResultList();
    }

    @Transactional
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Transactional
    public Book findByTitle(String title) {
        return em.createNamedQuery(Book.FIND_BY_TITLE, Book.class).setParameter(1, title + "%").getSingleResult();
    }

    @Transactional
    public void deleteAll() {
        em.createNativeQuery("delete from book").executeUpdate();
    }

    @Transactional
    public Book createBook(String title, String author) {
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        em.persist(book);
        em.flush();
        em.refresh(book);
        return book;
    }

    @Transactional
    public void loadData() {
        // reset and load all books:
        deleteAll();
        createBook("Java for Beginners", "Java Guru");
        createBook("Angular for Beginners", "Angular Guru");
        createBook("Quarkus for Beginners", "Quarkus Guru");
    }

}
