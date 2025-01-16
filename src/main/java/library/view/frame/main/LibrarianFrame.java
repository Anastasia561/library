package library.view.frame.main;

import library.controller.Controller;
import library.dto.BookForLibrarianDto;
import library.dto.BorrowingDto;
import library.dto.UserForLibrarianDto;
import library.view.frame.creational.UserCreationFrame;
import library.view.table_model.BookForLibrarianTableModel;
import library.view.table_model.BookForUserTableModel;
import library.view.table_model.BorrowingForLibrarianTableModel;
import library.view.table_model.BorrowingForUserTableModel;
import library.view.table_model.UserTableModel;

import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class LibrarianFrame extends JFrame {
    private JList<String> optionList;
    private JScrollPane pane;
    //private UserTableModel userTableModel;
//    private BookForUserTableModel bookTableModel;
//    private BorrowingForUserTableModel borrowingTableModel;
    private AbstractTableModel tableModel;
    private JPanel rightPanel;
    // private JTable userTable;
    private JPanel buttonsPanel;
    private final Controller controller;
    private final String userEmail;

    public LibrarianFrame(Controller controller, String userEmail) {
        this.controller = controller;
        this.userEmail = userEmail;
        setTitle("LIBRARY [Logged in as: "
                + controller.getUserNameByEmail(userEmail)
                + "] [" + userEmail + "]");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setLayout(new BorderLayout());

        rightPanel = createRightPanel();
        rightPanel.setVisible(false);
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

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
                //controller = new Controller(selectedModel);

                //controller.readDataFrom(selectedData).runModel();

//                if (!rightPanel.isVisible()) {
//                    rightPanel.setVisible(true);
//                }

                showTable(selectedOption);
//                pane = createUserTable();
//                buttonsPanel = createButtons(userTableModel);
//
//                rightPanel.add(pane);
//                rightPanel.add(buttonsPanel, BorderLayout.EAST);

            } else {
                JOptionPane.showMessageDialog(this, "Please select option first");
            }
        });
        return showButton;
    }

    private void showTable(String choice) {
        if (!rightPanel.isVisible()) {
            rightPanel.setVisible(true);
        }
        //rightPanel.remove(pane);
        switch (choice) {
            case "Users" -> {
                pane = createUserTable();
                //rightPanel = createRightPanel();
            }

            case "Books" -> {
                pane = createBookTable();
                //rightPanel = createRightPanel();
            }

            case "Borrowings" -> {
                pane = createBorrowingTable();
                //rightPanel = createRightPanel();
            }
        }

        buttonsPanel = createButtons();
        rightPanel.add(pane);
        rightPanel.add(buttonsPanel, BorderLayout.EAST);

//        JPanel newButtonPanel = (JPanel) createRightPanel().getComponent(1);
//        rightPanel.add(newButtonPanel, BorderLayout.SOUTH);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bPanel.setPreferredSize(new Dimension(getWidth() * 4 / 5 - 20, 40));

        if (tableModel instanceof UserTableModel) {
            JButton addUserButton = new JButton("Add User");
            addUserButton.setFocusable(false);
            addUserButton.addActionListener(e -> {
                new UserCreationFrame(controller, false);
                refreshTableAndButtons();
            });

            JButton addLibrarianButton = new JButton("Add Librarian");
            addLibrarianButton.setFocusable(false);
            addLibrarianButton.addActionListener(e -> {
                new UserCreationFrame(controller, true);
                refreshTableAndButtons();
            });

            bPanel.add(addLibrarianButton);
            bPanel.add(addUserButton);
        } else if (tableModel instanceof BookForLibrarianTableModel) {
            JButton addBookButton = new JButton("Add Book");
            addBookButton.setFocusable(false);
            addBookButton.addActionListener(e -> {
                //new BookCreationFrame(controller);
                refreshTableAndButtons();
            });

            bPanel.add(addBookButton);
        } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
            JButton addBorrowingButton = new JButton("Add Borrowing");
            addBorrowingButton.setFocusable(false);
            addBorrowingButton.addActionListener(e -> {
                //new BorrowingCreationFrame(controller, true);
                refreshTableAndButtons();
            });

            bPanel.add(addBorrowingButton);
        }

        rightPanel.add(bPanel, BorderLayout.SOUTH);
        rightPanel.add(tablePanel, BorderLayout.NORTH);

        return rightPanel;
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
//        int rowCount = userTableModel.getRowCount();
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
        System.out.println("Modify clicked for row: " + rowIndex);

    }

    private void onDeleteClicked(int rowIndex) {
        //System.out.println("Delete clicked for row: " + rowIndex);

        try {
            if (tableModel instanceof UserTableModel) {
                String userEmail = (String) tableModel.getValueAt(rowIndex, 1);
                controller.deleteUserByEmail(userEmail);
            } else if (tableModel instanceof BookForLibrarianTableModel) {
                String isbn = (String) tableModel.getValueAt(rowIndex, 4);
                controller.deleteBookByIsbn(isbn);
            } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
                Integer id = (Integer) tableModel.getValueAt(rowIndex, 0);
                controller.deleteBorrowingById(id);
            }

            refreshTableAndButtons();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

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
        List<BorrowingDto> borrowings = controller.getAllBorrowing();
        tableModel = new BorrowingForLibrarianTableModel(borrowings);
        return new JScrollPane(new JTable(tableModel));
    }

    private void refreshTableAndButtons() {
        List<UserForLibrarianDto> users = controller.getAllUserDto();
        List<BookForLibrarianDto> books = controller.getAllBookDto();
        List<BorrowingDto> borrowings = controller.getAllBorrowing();
        if (tableModel instanceof UserTableModel) {
            ((UserTableModel) tableModel).addUsers(users);
        } else if (tableModel instanceof BookForLibrarianTableModel) {
            ((BookForLibrarianTableModel) tableModel).addBooks(books);
        } else if (tableModel instanceof BorrowingForLibrarianTableModel) {
            ((BorrowingForLibrarianTableModel) tableModel).addBorrowings(borrowings);
        }

        JPanel newButtonsPanel = createButtons();
        rightPanel.remove(buttonsPanel);
        buttonsPanel = newButtonsPanel;
        rightPanel.add(buttonsPanel, BorderLayout.EAST);

        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
