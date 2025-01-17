package library;

import library.controller.Controller;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;
import library.view.frame.main.LibrarianFrame;

import javax.swing.*;

public class Runner {
    public void run() {
        SwingUtilities.invokeLater(() -> {
            new LibrarianFrame(new Controller(new UserService("hibernate.cfg.xml"),
                    new LibrarianService("hibernate.cfg.xml"),
                    new BookService("hibernate.cfg.xml"),
                    new BorrowingService("hibernate.cfg.xml")),
                    "alice.johnson@example.com");
        });
    }
}
