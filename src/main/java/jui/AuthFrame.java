package jui;

import db.DB;

import javax.swing.*;
import java.awt.*;

public class AuthFrame extends JFrame {
    private JTextField loginTxt;
    private JPasswordField passwordTxt;
    private JLabel msg;
    private JButton loginBtn;

    private final DB db;
    public AuthFrame(DB db) {
        super("Authorization");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();

        container.add(getContainer());

        this.db = db;
    }

    private Component getContainer() {
        JPanel panel = new JPanel(new GridBagLayout());
        loginTxt = new JTextField();
        getInputField(panel, "Login : ", loginTxt);

        passwordTxt = new JPasswordField();
        getInputField(panel, "Password : ", passwordTxt);

        msg = new JLabel();
        addMsgLabel(panel, msg);

        addButtons(panel);

        return panel;
    }

    private Component getInputField(Container container, String lblText, Component component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel(lblText);
        lbl.setHorizontalAlignment(JLabel.LEFT);
        container.add(lbl, constraints);

        constraints.weightx = 1.0f;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        container.add(component, constraints);

        return container;
    }

    private Component addMsgLabel(Container container, Component component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.weightx = 1.0f;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        container.add(component, constraints);

        return container;
    }

    private Component addButtons(Container container) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.weightx = 1.0f;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        loginBtn = new JButton("Login");
        loginBtn.addActionListener((x) -> connect());
        panel.add(loginBtn);

        JButton exitBtn = new JButton("Cancel");
        exitBtn.addActionListener((x) -> System.exit(0));
        panel.add(exitBtn);

        panel.add(exitBtn);

        container.add(panel, constraints);

        return container;
    }

    private void connect() {
        String username = loginTxt.getText();
        String password = passwordTxt.getText();

        if(username.isBlank() || password.isBlank()) {
            msg.setText("All fields are required");
        } else if(!db.checkUser(username, password)) {
            msg.setText("Access denied");
        } else {
            msg.setText("");
        }
    }
}
