package library;

import library.controller.Controller;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;
import library.view.frame.main.StartFrame;

import javax.swing.*;

public class Runner {
    private static final String CONF = "hibernate.cfg.xml";

    public void run() {
        SwingUtilities.invokeLater(() -> {
            new StartFrame(new Controller(UserService.getInstance(CONF),
                    LibrarianService.getInstance(CONF),
                    BookService.getInstance(CONF),
                    BorrowingService.getInstance(CONF)));
        });
    }
}
