package library.view.table_model;

import library.dto.UserForLibrarianDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * A table model for displaying user information for librarians.
 * This model extends {@code AbstractTableModel} and is used to represent
 * a list of {@code UserForLibrarianDto} objects in a tabular format.
 * <p>
 * The table includes the following columns:
 * 1. Name
 * 2. Email
 * 3. Phone Number
 * 4. Address
 * 5. Is Librarian
 */
public class UserTableModel extends AbstractTableModel {

    /**
     * Column names for the table.
     */
    private final String[] columnNames = {"Name", "Email", "Phone Number", "Address", "Is librarian"};

    /**
     * List of users displayed in the table.
     */
    private List<UserForLibrarianDto> users;

    /**
     * Constructor to initialize the table model with a list of users.
     *
     * @param users the initial list of {@code UserForLibrarianDto} objects to display.
     */
    public UserTableModel(List<UserForLibrarianDto> users) {
        this.users = users;
    }

    /**
     * Returns the number of rows in the table.
     * This corresponds to the number of users in the list.
     *
     * @return the number of rows (users).
     */
    @Override
    public int getRowCount() {
        return users.size();
    }

    /**
     * Returns the number of columns in the table.
     *
     * @return the number of columns.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Retrieves the name of the column at the specified index.
     *
     * @param column the index of the column.
     * @return the name of the column.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Retrieves the value of a cell at the specified row and column index.
     *
     * @param rowIndex    the index of the row.
     * @param columnIndex the index of the column.
     * @return the value at the specified cell.
     */
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

    /**
     * Updates the list of users in the model and refreshes the table view.
     *
     * @param users the new list of {@code UserForLibrarianDto} objects.
     */
    public void addUsers(List<UserForLibrarianDto> users) {
        this.users = users;
        fireTableDataChanged();
    }
}
