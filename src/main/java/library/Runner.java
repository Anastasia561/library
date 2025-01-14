package library;

import library.controller.Controller;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;
import library.view.frame.main.StartFrame;

import javax.swing.*;

public class Runner {
    public void run() {
        SwingUtilities.invokeLater(() -> {
            new StartFrame(new Controller(new UserService(), new LibrarianService(),
                    new BookService(), new BorrowingService()));
        });
    }
}
