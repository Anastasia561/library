package library;

import library.controller.Controller;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;
import library.view.frame.main.StartFrame;

import javax.swing.*;

/**
 * The {@code Runner} class serves as the entry point to initialize and launch the application.
 * It creates the necessary services and sets up the main user interface.
 */
public class Runner {

    /**
     * The configuration file name for Hibernate.
     */
    private static final String CONF = "hibernate.cfg.xml";


    /**
     * Launches the application by initializing the {@code StartFrame} within a Swing UI thread.
     * The required services for managing users, librarians, books, and borrowings are instantiated
     * and passed to the {@code StartFrame}.
     */
    public void run() {
        SwingUtilities.invokeLater(() -> {
            new StartFrame(new Controller(UserService.getInstance(CONF),
                    LibrarianService.getInstance(CONF),
                    BookService.getInstance(CONF),
                    BorrowingService.getInstance(CONF)));
        });
    }
}
