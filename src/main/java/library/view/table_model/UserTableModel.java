package library.view.table_model;

import library.dto.UserForLibrarianDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Email", "Phone Number", "Address", "Is librarian"};
    private List<UserForLibrarianDto> users;

    public UserTableModel(List<UserForLibrarianDto> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserForLibrarianDto user = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> user.getName();
            case 1 -> user.getEmail();
            case 2 -> user.getPhoneNumber();
            case 3 -> user.getAddress();
            case 4 -> user.getIsLibrarian();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void addUser(List<UserForLibrarianDto> users) {
        this.users = users;
        fireTableDataChanged();
    }
}
