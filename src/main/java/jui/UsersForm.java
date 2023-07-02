package jui;

import db.DB;

import javax.swing.*;
import java.awt.*;

public class UsersForm extends JFrame {
    private final DB db;
    private UserTableModel model;
    private JButton addBtn;

    public UsersForm(DB db) {
        super("Users");
        super.setSize(500, 500);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(new GridLayout(1, 1, 5, 5));

        this.db = db;

        container.add(getContainer());

    }

    private Component getContainer() {
        JPanel panel = new JPanel(new GridBagLayout());
        addUsersTable(panel);
        panel.add(getButton());
        return panel;
    }
    private Component addUsersTable(Container container) {
        var users = db.getUsers();
        model = new UserTableModel(users);

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        container.add(scrollPane, constraints);
        return container;
    }

    private Component getButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add new user");
        addBtn.addActionListener((x) -> addUser());
        panel.add(addBtn);

        return panel;
    }

    private void addUser() {
        NewUserFrame frame = new NewUserFrame(db);
        frame.onUserAdded.add(() -> model.update(db.getUsers()));
        frame.setVisible(true);
    }
}
