package library.view.frame.main;

import library.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class StartFrame extends JFrame {
    Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final Controller controller;

    public StartFrame(Controller controller) {
        this.controller = controller;

        setTitle("Log In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(30);

        JButton loginButton = createLogInButton(emailField);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createLogInButton(JTextField emailField) {
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            StartFrame.this, "Email field cannot be empty.");
                }

                try {
                    validateEmail(email);
                    controller.getUserNameByEmail(email);
                    if (controller.checkIfIsLibrarian(email)) {
                        new LibrarianFrame(controller, email);
                    } else {
                        new UserFrame(controller, email);
                    }
                    StartFrame.this.dispose();
                } catch (Exception b) {
                    JOptionPane.showMessageDialog(StartFrame.this, b.getMessage());
                }
            }
        });
        return loginButton;
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email format is invalid");
        }
    }
}
