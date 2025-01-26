package library.view.table_model;

import library.dto.BorrowingDto;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

/**
 * A table model for displaying borrowing information for librarians.
 * This model extends {@code AbstractTableModel} and is used to represent
 * a list of {@code BorrowingDto} objects in a tabular format.
 * <p>
 * The table includes the following columns:
 * 1. Id
 * 2. User Name
 * 3. User Email
 * 4. Copy Number
 * 5. Book Title
 * 6. Author
 * 7. ISBN
 * 8. Borrow Date
 * 9. Return Date
 */
public class BorrowingForLibrarianTableModel extends AbstractTableModel {

    /**
     * Column names for the table.
     */
    private final String[] columnNames = {"Id", "User Name", "User Email", "Copy Number", "Book Title",
            "Author", "isbn", "Borrow Date", "Return Date"};

    /**
     * List of borrowings displayed in the table.
     */
    private List<BorrowingDto> borrowings;

    /**
     * Constructor to initialize the table model with a list of borrowings.
     *
     * @param borrowings the initial list of {@code BorrowingDto} objects to display.
     */
    public BorrowingForLibrarianTableModel(List<BorrowingDto> borrowings) {
        this.borrowings = borrowings;
    }

    /**
     * Returns the number of rows in the table.
     * This corresponds to the number of borrowings in the list.
     *
     * @return the number of rows (borrowings).
     */
    @Override
    public int getRowCount() {
        return borrowings.size();
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
        BorrowingDto dto = borrowings.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> dto.getId();
            case 1 -> dto.getUserName();
            case 2 -> dto.getUserEmail();
            case 3 -> dto.getCopyNumber();
            case 4 -> dto.getBookTitle();
            case 5 -> dto.getAuthor();
            case 6 -> dto.getIsbn();
            case 7 -> dto.getBorrowDate();
            case 8 -> {
                LocalDate returnDate = dto.getReturnDate();
                if (returnDate == null) {
                    yield "-";
                }
                yield returnDate;
            }
            default -> null;
        };
    }

    /**
     * Updates the list of borrowings in the model and refreshes the table view.
     *
     * @param borrowings the new list of {@code BorrowingDto} objects.
     */
    public void addBorrowings(List<BorrowingDto> borrowings) {
        this.borrowings = borrowings;
        fireTableDataChanged();
    }
}
