package library.view.frame.main;

import library.controller.Controller;
import library.dto.BookForLibrarianDto;
import library.dto.BorrowingDto;
import library.dto.UserForLibrarianDto;
import library.view.frame.creational.BookCreationFrame;
import library.view.frame.creational.BorrowingCreationFrame;
import library.view.frame.creational.UserCreationFrame;
import library.view.table_model.BookForLibrarianTableModel;
import library.view.table_model.BorrowingForLibrarianTableModel;
import library.view.table_model.UserTableModel;

import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class LibrarianFrame extends JFrame {
    private JList<String> optionList;
    private JScrollPane pane;
    private AbstractTableModel tableModel;
    private JPanel rightPanel;
    private JPanel buttonsPanel;
    private JPanel rowButtonsPanel;
    private final Controller controller;
    private final String userEmail;

    public LibrarianFrame(Controller controller, String userEmail) {
        this.controller = controller;
        this.userEmail = userEmail;

        setTitle("LIBRARY [Logged in as: " + controller.getUserNameByEmail(userEmail) + "] [" + userEmail + "]");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        rightPanel = createRightPanel();
        rightPanel.setVisible(false);
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(getWidth() / 5, getHeight()));
        leftPanel.setBackground(Color.LIGHT_GRAY);

        optionList = new JList<>(new String[]{"Users", "Books", "Borrowings"});

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        JButton showButton = createShowButton();
        JButton logOutButton = createLogOutButton();

        buttonsPanel.add(logOutButton);
        buttonsPanel.add(showButton);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.setPreferredSize(new Dimension(getWidth() / 5 - 10, getHeight() - 100));
        listsPanel.setBackground(Color.LIGHT_GRAY);
        listsPanel.add(new JScrollPane(optionList));

        leftPanel.add(new JLabel("Select option"), BorderLayout.NORTH);
        leftPanel.add(listsPanel, BorderLayout.CENTER);
        leftPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JButton createShowButton() {
        JButton showButton = new JButton("Show");
        showButton.setFocusable(false);
        showButton.addActionListener(e -> {
            String selectedOption = optionList.getSelectedValue();
            if (selectedOption != null) {
                showTable(selectedOption);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an option first");
            }
        });
        return showButton;
    }

    private void showTable(String choice) {
        if (!rightPanel.isVisible()) {
            rightPanel.setVisible(true);
        }
        rightPanel.removeAll();
        switch (choice) {
            case "Users" -> pane = createUserTable();
            case "Books" -> pane = createBookTable();
            case "Borrowings" -> pane = createBorrowingTable();
        }
        buttonsPanel = createButtons();
        rowButtonsPanel = createRowButtons();
        rightPanel.add(pane, BorderLayout.CENTER);
        rightPanel.add(rowButtonsPanel, BorderLayout.EAST);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private JPanel createRightPanel() {
        return new JPanel(new BorderLayout());
    }

    private JButton createLogOutButton() {
        JButton logOutButton = new JButton("Log out");
        logOutButton.setFocusable(false);
        logOutButton.addActionListener(e -> {
            new StartFrame(controller);
            this.dispose();
        });
        return logOutButton;
    }

    private JPanel createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        if (tableModel instanceof UserTableModel) {
            JButton addUserButton = new JButton("Add User");
            addUserButton.setFocusable(false);
            addUserButton.addActionListener(e -> {
                new UserCreationFrame(controller, false, false, null);
                showTable("Users");
                //refreshTableAndButtons();
            });

            JButton addLibrarianButton = new JButton("Add Librarian");
            addLibrarianButton.setFocusable(false);
            addLibrarianButton.addActionListener(e -> {
                new UserCreationFrame(controller, true, false, null);
                showTable("Users");
                //refreshTableAndButtons();
            });

            buttonPanel.add(addUserButton);
            buttonPanel.add(addLibrarianButton);
        } else if (tableModel instanceof BookForLibrarianTableModel) {
            JButton addBookButton = new JButton("Add Book");
            addBookButton.setFocusable(false);
            addBookButton.addActionListener(e -> {
                new BookCreationFrame(controller, false, null);
                //refreshTableAndButtons();
            });

            buttonPanel.add(addBookButton);
        } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
            JButton addBorrowingButton = new JButton("Add Borrowing");
            addBorrowingButton.setFocusable(false);
            addBorrowingButton.addActionListener(e -> {
                new BorrowingCreationFrame(controller, false, null);
                //refreshTableAndButtons();
            });

            buttonPanel.add(addBorrowingButton);
        }
        return buttonPanel;
    }

    private JScrollPane createUserTable() {
        List<UserForLibrarianDto> users = controller.getAllUserDto();
        tableModel = new UserTableModel(users);
        return new JScrollPane(new JTable(tableModel));
    }

    private JScrollPane createBookTable() {
        List<BookForLibrarianDto> books = controller.getAllBookDto();
        tableModel = new BookForLibrarianTableModel(books);
        return new JScrollPane(new JTable(tableModel));
    }

    private JScrollPane createBorrowingTable() {
        List<BorrowingDto> borrowings = controller.getAllBorrowingDto();
        tableModel = new BorrowingForLibrarianTableModel(borrowings);
        return new JScrollPane(new JTable(tableModel));
    }

    private JPanel createRowButtons() {
        int rowCount = tableModel.getRowCount();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(60, rightPanel.getHeight() - 40));

        JLabel label = new JLabel("Mod  Del");
        label.setFont(new Font("Arial", NORMAL, 10));
        buttonPanel.add(label);

        for (int i = 0; i < rowCount; i++) {
            JButton modifyButton = new JButton();
            modifyButton.setFocusable(false);
            modifyButton.setPreferredSize(new Dimension(20, 12));
            modifyButton.setBackground(Color.ORANGE);
            JButton deleteButton = new JButton();
            deleteButton.setFocusable(false);
            deleteButton.setPreferredSize(new Dimension(20, 12));
            deleteButton.setBackground(Color.RED);

            int rowIndex = i;
            modifyButton.addActionListener(e -> onModifyClicked(rowIndex));
            deleteButton.addActionListener(e -> onDeleteClicked(rowIndex));

            buttonPanel.add(modifyButton);
            buttonPanel.add(deleteButton);

        }
        return buttonPanel;
    }

    private void onModifyClicked(int rowIndex) {
        try {
            if (tableModel instanceof UserTableModel) {
                String userEmail = (String) tableModel.getValueAt(rowIndex, 1);
                Boolean isLibrarian = (Boolean) tableModel.getValueAt(rowIndex, 4);
                new UserCreationFrame(controller, isLibrarian, true, userEmail);
                showTable("Users");
            } else if (tableModel instanceof BookForLibrarianTableModel) {
                String isbn = (String) tableModel.getValueAt(rowIndex, 4);
                new BookCreationFrame(controller, true, isbn);
                showTable("Books");
            } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
                Integer id = (Integer) tableModel.getValueAt(rowIndex, 0);
                new BorrowingCreationFrame(controller, true, id);
                showTable("Borrowings");
            }

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void onDeleteClicked(int rowIndex) {
        try {
            if (tableModel instanceof UserTableModel) {
                String userEmail = (String) tableModel.getValueAt(rowIndex, 1);
                controller.deleteUserByEmail(userEmail);
                showTable("Users");
            } else if (tableModel instanceof BookForLibrarianTableModel) {
                String isbn = (String) tableModel.getValueAt(rowIndex, 4);
                controller.deleteBookByIsbn(isbn);
                showTable("Books");
            } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
                Integer id = (Integer) tableModel.getValueAt(rowIndex, 0);
                controller.deleteBorrowingById(id);
                showTable("Borrowings");
            }

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }
}
