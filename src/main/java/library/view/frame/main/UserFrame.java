package library.view.frame.main;

import library.controller.Controller;
import library.dto.BookForUserDto;
import library.dto.BorrowingDto;
import library.view.table_model.BookForUserTableModel;
import library.view.table_model.BorrowingForUserTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private JList<String> optionList;
    private final JPanel rightPanel;
    private final CardLayout cardLayout;
    private final Controller controller;
    private final String userEmail;

    public UserFrame(Controller controller, String userEmail) {
        this.controller = controller;
        this.userEmail = userEmail;
        setTitle("LIBRARY [Logged in as: "
                + controller.getUserNameByEmail(userEmail)
                + "] [" + userEmail + "]");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);

        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        rightPanel.add(new JPanel(), "EMPTY");
        rightPanel.add(createBookTable(controller.getAvailableBookForUserDto()), "AVAILABLE_BOOKS");
        rightPanel.add(createBookTable(controller.getAllBookForUserDto()), "ALL_BOOKS");
        rightPanel.add(createBorrowingsTable(), "BORROWINGS");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(getWidth() / 3, getHeight()));
        leftPanel.setBackground(Color.LIGHT_GRAY);

        optionList = new JList<>(new String[]{"All books", "Available books", "Borrowing history"});

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        JButton showButton = createShowButton();
        JButton logOutButton = createLogOutButton();

        buttonsPanel.add(logOutButton);
        buttonsPanel.add(showButton);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.setPreferredSize(new Dimension(getWidth() / 3 - 10, getHeight() - 100));
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
                switch (selectedOption) {
                    case "Available books" -> cardLayout.show(rightPanel, "AVAILABLE_BOOKS");
                    case "All books" -> cardLayout.show(rightPanel, "ALL_BOOKS");
                    case "Borrowing history" -> cardLayout.show(rightPanel, "BORROWINGS");
                    default -> cardLayout.show(rightPanel, "EMPTY");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an option first");
            }
        });
        return showButton;
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

    private JScrollPane createBookTable(List<BookForUserDto> books) {
        BookForUserTableModel bookTableModel = new BookForUserTableModel(books);
        JTable bookTable = new JTable(bookTableModel);
        JScrollPane pane = new JScrollPane(bookTable);
        pane.setPreferredSize(new Dimension(getWidth() * 2 / 3 - 30, getHeight()));
        return pane;
    }

    private JScrollPane createBorrowingsTable() {
        List<BorrowingDto> borrowings = controller.getAllBorrowingsForUserByEmail(userEmail);
        BorrowingForUserTableModel borrowingTableModel = new BorrowingForUserTableModel(borrowings);
        JTable bookTable = new JTable(borrowingTableModel);
        JScrollPane pane = new JScrollPane(bookTable);
        pane.setPreferredSize(new Dimension(getWidth() * 2 / 3 - 30, getHeight()));
        return pane;
    }
}
