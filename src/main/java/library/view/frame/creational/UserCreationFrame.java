package library.view.frame.creational;

import library.controller.Controller;
import library.dto.UserForLibrarianDto;
import library.entity.Librarian;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class UserCreationFrame extends JFrame {
    private final Controller controller;
    private final Boolean isLibrarian;
    private final Boolean isModification;
    private final String email;
    private UserForLibrarianDto dto = null;

    public UserCreationFrame(Controller controller, Boolean isLibrarian,
                             Boolean isModification, String email) {
        this.controller = controller;
        this.isLibrarian = isLibrarian;
        this.isModification = isModification;
        this.email = email;
        create();
    }

    private void create() {
        if (isModification) {
            setTitle("Modify user");
            dto = controller.getUserForLibrarianDtoByEmail(email);
        } else {
            if (isLibrarian) {
                setTitle("Create new librarian");
            } else {
                setTitle("Create new user");
            }
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 5, 10));

        formPanel.add(new JLabel("Name:"));
        JTextField nameField = (isModification ? new JTextField(dto.getName()) : new JTextField());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        JTextField emailField;
        if (isModification) {
            emailField = new JTextField(dto.getEmail());
            emailField.setEditable(false);
        } else {
            emailField = new JTextField();
        }
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = (isModification ? new JTextField(dto.getPhoneNumber()) : new JTextField());
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Address:"));
        JTextField addressField = (isModification ? new JTextField(dto.getAddress()) : new JTextField());
        formPanel.add(addressField);

        JTextField positionField;
        JDatePickerImpl datePicker;
        Librarian librarian = null;
        if (isLibrarian) {
            if (isModification) {
                librarian = controller.getLibrarianByEmail(email);
            }
            formPanel.add(new JLabel("Position"));
            positionField = (isModification ? new JTextField(librarian.getPosition()) : new JTextField());
            formPanel.add(positionField);

            UtilDateModel model = new UtilDateModel();
            Properties properties = new Properties();
            properties.put("text.today", "Today");
            properties.put("text.month", "Month");
            properties.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
            if (isModification) {
                datePanel.getModel().setDate(librarian.getEmploymentDate().getYear(),
                        librarian.getEmploymentDate().getMonthValue() - 1,
                        librarian.getEmploymentDate().getDayOfMonth());
                datePanel.getModel().setSelected(true);
            }
            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            formPanel.add(new JLabel("Employment date:"));
            formPanel.add(datePicker);
        } else {
            positionField = null;
            datePicker = null;

        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneField.getText();
            String address = addressField.getText();
            String position = "";
            LocalDate date = null;
            if (isLibrarian) {
                position = positionField.getText();
                Date dateD = (Date) datePicker.getModel().getValue();
                if (dateD != null) {
                    date = dateD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
            }

            if (email.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()
                    || address.isEmpty() || (isLibrarian && (position.isEmpty() || date == null))) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
            } else {
                try {
                    UserForLibrarianDto dto = UserForLibrarianDto.builder()
                            .name(name)
                            .email(email)
                            .phoneNumber(phoneNumber)
                            .address(address)
                            .isLibrarian(isLibrarian)
                            .build();
                    if (isModification) {
                        if (isLibrarian) {
                            controller.updateLibrarian(dto, position, date);
                        } else {
                            controller.updateUser(dto);
                        }
                    } else {
                        if (isLibrarian) {
                            controller.createLibrarian(dto, date, position);
                        } else {
                            controller.createUser(dto);
                        }
                    }

                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    addressField.setText("");
                    if (isLibrarian) {
                        positionField.setText("");
                        datePicker.getModel().setValue(null);
                    }
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
