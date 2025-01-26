package library.view.table_model;

import library.dto.BorrowingDto;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

/**
 * A table model for displaying borrowing information for users.
 * This model extends {@code AbstractTableModel} and is used to represent
 * a list of {@code BorrowingDto} objects in a tabular format.
 * <p>
 * The table includes the following columns:
 * 1. Title
 * 2. Author
 * 3. Copy Number
 * 4. Borrow Date
 * 5. Return Date
 */
public class BorrowingForUserTableModel extends AbstractTableModel {

    /**
     * Column names for the table.
     */
    private final String[] columnNames = {"Title", "Author", "Copy Number", "Borrow Date", "Return Date"};

    /**
     * List of borrowings displayed in the table.
     */
    private List<BorrowingDto> borrowings;

    /**
     * Constructor to initialize the table model with a list of borrowings.
     *
     * @param borrowings the initial list of {@code BorrowingDto} objects to display.
     */
    public BorrowingForUserTableModel(List<BorrowingDto> borrowings) {
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
            case 0 -> dto.getBookTitle();
            case 1 -> dto.getAuthor();
            case 2 -> dto.getCopyNumber();
            case 3 -> dto.getBorrowDate();
            case 4 -> {
                LocalDate returnDate = dto.getReturnDate();
                if (returnDate == null) {
                    yield "-";
                }
                yield returnDate;
            }
            default -> null;
        };
    }
}
