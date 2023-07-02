package jui;

import models.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private List<User> users;
    private final String[] columns;
    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList<>(users);
        columns = new String[]{ "Id", "Login" };
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) {
            return users.get(rowIndex).getId();
        } else if(columnIndex == 1) {
            return users.get(rowIndex).getUserName();
        }
        return null;
    }

    public void update(Collection<User> users) {
        this.users.clear();
        this.users.addAll(users);
        fireTableDataChanged();
    }
}
