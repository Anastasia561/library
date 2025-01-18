package library.view.frame.creational;

import library.controller.Controller;
import library.dto.BorrowingDto;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class BorrowingCreationFrame extends JFrame {
    private final Controller controller;
    private final Boolean isModification;
    private final Integer id;
    private BorrowingDto dto = null;

    public BorrowingCreationFrame(Controller controller, Boolean isModification, Integer id) {
        this.controller = controller;
        this.isModification = isModification;
        this.id = id;
        create();
    }

    private void create() {
        if (isModification) {
            setTitle("Modify borrowing");
            dto = controller.getBorrowingDtoById(id);
        } else {
            setTitle("Create new borrowing");
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 5, 10));

        formPanel.add(new JLabel("User name:"));
        JTextField userNameField = (isModification ? new JTextField(dto.getUserName()) : new JTextField());
        formPanel.add(userNameField);

        formPanel.add(new JLabel("User email:"));
        JTextField userEmailField = (isModification ? new JTextField(dto.getUserEmail()) : new JTextField());
        formPanel.add(userEmailField);

        formPanel.add(new JLabel("Copy number:"));
        JTextField copyNumberField = (isModification ? new JTextField("" + dto.getCopyNumber()) : new JTextField());
        formPanel.add(copyNumberField);

        formPanel.add(new JLabel("Book title:"));
        JTextField bookTitleField = (isModification ? new JTextField(dto.getBookTitle()) : new JTextField());
        formPanel.add(bookTitleField);

        formPanel.add(new JLabel("Author:"));
        JTextField authorField = (isModification ? new JTextField(dto.getAuthor()) : new JTextField());
        formPanel.add(authorField);

        formPanel.add(new JLabel("Isbn:"));
        JTextField isbnField = (isModification ? new JTextField(dto.getIsbn()) : new JTextField());
        formPanel.add(isbnField);

        UtilDateModel model1 = new UtilDateModel();
        Properties properties1 = new Properties();
        properties1.put("text.today", "Today");
        properties1.put("text.month", "Month");
        properties1.put("text.year", "Year");
        JDatePanelImpl borrowDatePanel = new JDatePanelImpl(model1, properties1);
        if (isModification) {
            borrowDatePanel.getModel().setDate(dto.getBorrowDate().getYear(),
                    dto.getBorrowDate().getMonthValue() - 1,
                    dto.getBorrowDate().getDayOfMonth());
            borrowDatePanel.getModel().setSelected(true);
        }
        JDatePickerImpl borrowDatePicker = new JDatePickerImpl(borrowDatePanel, new DateLabelFormatter());
        formPanel.add(new JLabel("Borrow date:"));
        formPanel.add(borrowDatePicker);

        UtilDateModel model2 = new UtilDateModel();
        Properties properties2 = new Properties();
        properties2.put("text.today", "Today");
        properties2.put("text.month", "Month");
        properties2.put("text.year", "Year");
        JDatePanelImpl returnDatePanel = new JDatePanelImpl(model2, properties2);
        if (isModification && dto.getReturnDate() != null) {
            returnDatePanel.getModel().setDate(dto.getReturnDate().getYear(),
                    dto.getReturnDate().getMonthValue() - 1,
                    dto.getReturnDate().getDayOfMonth());
            returnDatePanel.getModel().setSelected(true);
        }
        JDatePickerImpl returnDatePicker = new JDatePickerImpl(returnDatePanel, new DateLabelFormatter());
        formPanel.add(new JLabel("Return date:"));
        formPanel.add(returnDatePicker);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);

        saveButton.addActionListener(e -> {
            String userName = userEmailField.getText();
            String userEmail = userEmailField.getText();
            String bookTitle = bookTitleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int copyNumber = 0;
            LocalDate borrowDate = null;
            LocalDate returnDate = null;
            try {
                copyNumber = Integer.parseInt(copyNumberField.getText());
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(this,
                        "Numeric field have incorrect data input");
            }
            Date dateD1 = (Date) borrowDatePicker.getModel().getValue();
            if (dateD1 != null) {
                borrowDate = dateD1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            Date dateD2 = (Date) returnDatePicker.getModel().getValue();
            if (dateD2 != null) {
                returnDate = dateD2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            if (userName.isEmpty() || userEmail.isEmpty() || bookTitle.isEmpty()
                    || author.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
            } else {
                try {
                    BorrowingDto dto = BorrowingDto.builder()
                            .id(id)
                            .userName(userName)
                            .userEmail(userEmail)
                            .copyNumber(copyNumber)
                            .bookTitle(bookTitle)
                            .author(author)
                            .isbn(isbn)
                            .borrowDate(borrowDate)
                            .returnDate(returnDate)
                            .build();
                    if (isModification) {
                        controller.updateBorrowing(dto);
                    } else {
                        controller.createBorrowing(dto);
                    }

                    userNameField.setText("");
                    userEmailField.setText("");
                    copyNumberField.setText("");
                    bookTitleField.setText("");
                    authorField.setText("");
                    isbnField.setText("");
                    borrowDatePicker.getModel().setValue(null);
                    returnDatePicker.getModel().setValue(null);
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
