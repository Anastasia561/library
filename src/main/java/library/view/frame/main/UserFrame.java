package library.view.frame.main;

import library.controller.Controller;
import library.dto.BookForUserDto;
import library.dto.BorrowingDto;
import library.view.table_model.BookTableModel;
import library.view.table_model.BorrowingTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private JList<String> optionList;
    private JPanel rightPanel;
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

        rightPanel = new JPanel();
        rightPanel.setVisible(false);
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

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

                if (!rightPanel.isVisible()) {
                    rightPanel.setVisible(true);
                }

                JScrollPane pane;

                if (selectedOption.equals("Available books")) {
                    pane = createBookTable(controller.getAvailableBookForUserDto());
                } else if (selectedOption.equals("All books")) {
                    pane = createBookTable(controller.getAllBookForUserDto());
                } else {
                    pane = createBorrowingsTable();
                }
                rightPanel.removeAll();
                rightPanel.add(pane);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select option first");
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
        BookTableModel bookTableModel = new BookTableModel(books);
        JTable bookTable = new JTable(bookTableModel);
        JScrollPane pane = new JScrollPane(bookTable);
        pane.setPreferredSize(new Dimension(getWidth() * 2 / 3 - 30, getHeight()));
        return pane;
    }

    private JScrollPane createBorrowingsTable() {
        List<BorrowingDto> borrowings = controller.getAllBorrowingsForUserByEmail(userEmail);
        BorrowingTableModel borrowingTableModel = new BorrowingTableModel(borrowings);
        JTable bookTable = new JTable(borrowingTableModel);
        JScrollPane pane = new JScrollPane(bookTable);
        pane.setPreferredSize(new Dimension(getWidth() * 2 / 3 - 30, getHeight()));
        return pane;
    }
}
