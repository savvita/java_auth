package jui;

import db.DB;

import javax.swing.*;
import java.awt.*;

public class NewUserFrame extends JFrame {
    private JTextField loginTxt;
    private JPasswordField passwordTxt;
    private JPasswordField confirmPasswordTxt;
    private JLabel msg;
    private JButton addBtn;

    public events.Event onUserAdded = new events.Event();

    private final DB db;
    public NewUserFrame(DB db) {
        super("Add new user");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        confirmPasswordTxt = new JPasswordField();
        getInputField(panel, "Confirm password : ", confirmPasswordTxt);

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

        addBtn = new JButton("Add");
        addBtn.addActionListener((x) -> addNewUser());
        panel.add(addBtn);

        JButton exitBtn = new JButton("Cancel");
        exitBtn.addActionListener((x) -> this.setVisible(false));
        panel.add(exitBtn);

        panel.add(exitBtn);

        container.add(panel, constraints);

        return container;
    }

    private void addNewUser() {
        String username = loginTxt.getText();
        String password = passwordTxt.getText();
        String confirmPassword = confirmPasswordTxt.getText();

        if(username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            msg.setText("All fields are required");
        } else if(!password.equals(confirmPassword)) {
            msg.setText("Passwords do not match");
        } else if(!db.checkUsername(username)) {
            msg.setText("Login is already registered");
        } else {
            if(!db.addUser(username, password)) {
                msg.setText("Cannot add new user");
            } else {
                msg.setText("");
                onUserAdded.invoke();
                this.setVisible(false);
            }
        }
    }
}
