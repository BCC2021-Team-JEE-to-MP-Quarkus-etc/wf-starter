package com.baloise.codecamp.wildfly.books;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class StartUp {
    private static Logger logger = Logger.getLogger(StartUp.class.getName());
    @Inject
    private BookService bookService;

    public void onStart(@Observes @Initialized(ApplicationScoped.class) Object pointless) {
        logger.info("loadData");
        bookService.loadData();
    }

}
