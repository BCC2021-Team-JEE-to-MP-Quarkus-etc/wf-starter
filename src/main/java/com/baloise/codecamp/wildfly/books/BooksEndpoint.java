package com.baloise.codecamp.wildfly.books;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/books")
@ApplicationScoped
public class BooksEndpoint {

    @Inject
    private BookService bookService;

    @GET
    @Produces("application/json")
    public List<Book> list() {
        return bookService.list();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Book findById(@PathParam("id") Long id) {
        return bookService.findById(id);
    }

    @GET
    @Path("{title}")
    @Produces("application/json")
    public Book findByTitle(@PathParam("title") String title) {
        return bookService.findByTitle(title);
    }

    @GET
    @Path("/loadData")
    public void loadData() {
        bookService.loadData();
    }
}