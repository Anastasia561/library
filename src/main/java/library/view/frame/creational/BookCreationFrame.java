package library.view.frame.creational;

import library.controller.Controller;
import library.dto.BookForLibrarianDto;

import javax.swing.*;
import java.awt.*;

public class BookCreationFrame extends JFrame {
    private final Controller controller;
    private final Boolean isModification;
    private final String isbn;
    private BookForLibrarianDto dto = null;

    public BookCreationFrame(Controller controller, Boolean isModification, String isbn) {
        this.controller = controller;
        this.isModification = isModification;
        this.isbn = isbn;
        create();
    }

    private void create() {
        if (isModification) {
            setTitle("Modify book");
            dto = controller.getBookForLibrarianDtoByIsbn(isbn);
        } else {
            setTitle("Create new book");
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 5, 10));

        formPanel.add(new JLabel("Title:"));
        JTextField titleField = (isModification ? new JTextField(dto.getTitle()) : new JTextField());
        formPanel.add(titleField);

        formPanel.add(new JLabel("Author:"));
        JTextField authorField = (isModification ? new JTextField(dto.getAuthor()) : new JTextField());
        formPanel.add(authorField);

        formPanel.add(new JLabel("Publisher Name:"));
        JTextField publisherField = (isModification ? new JTextField(dto.getPublisherName()) : new JTextField());
        formPanel.add(publisherField);

        formPanel.add(new JLabel("Publication Year:"));
        JTextField yearField = (isModification ? new JTextField("" + dto.getPublicationYear()) : new JTextField());
        formPanel.add(yearField);

        formPanel.add(new JLabel("Isbn:"));
        JTextField isbnField;
        if (isModification) {
            isbnField = new JTextField(dto.getIsbn());
            isbnField.setEditable(false);
        } else {
            isbnField = new JTextField();
        }
        formPanel.add(isbnField);

        formPanel.add(new JLabel("All copies count:"));
        JTextField allCopiesField = (isModification ? new JTextField("" + dto.getAllCopiesCount()) : new JTextField());
        formPanel.add(allCopiesField);

        formPanel.add(new JLabel("Available copies count:"));
        JTextField avalCopiesField = (isModification ? new JTextField("" + dto.getAllCopiesCount()) : new JTextField());
        formPanel.add(avalCopiesField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = publisherField.getText();
            String isbn = isbnField.getText();
            int year = 0;
            long allCopiesCount = 0;
            long avalCopiesCount = 0;
            try {
                year = Integer.parseInt(yearField.getText());
                allCopiesCount = Long.parseLong(allCopiesField.getText());
                avalCopiesCount = Long.parseLong(avalCopiesField.getText());
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(this,
                        "Numeric field have incorrect data input");
            }

            if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
            } else {
                try {
                    BookForLibrarianDto dto = BookForLibrarianDto.builder()
                            .title(title)
                            .author(author)
                            .publisherName(publisher)
                            .publicationYear(year)
                            .isbn(isbn)
                            .allCopiesCount(allCopiesCount)
                            .availableCopiesCount(avalCopiesCount)
                            .build();
                    if (isModification) {
                        controller.updateBook(dto);
                    } else {
                        controller.createBook(dto);
                    }

                    titleField.setText("");
                    authorField.setText("");
                    publisherField.setText("");
                    yearField.setText("");
                    isbnField.setText("");
                    allCopiesField.setText("");
                    avalCopiesField.setText("");
                } catch (RuntimeException a) {
                    JOptionPane.showMessageDialog(this, a.getMessage());
                }
            }
        });

        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
