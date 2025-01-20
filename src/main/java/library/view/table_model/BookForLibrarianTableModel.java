package library.view.table_model;

import library.dto.BookForLibrarianDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * A table model for displaying book information for librarians.
 * This model extends {@code AbstractTableModel} and is used to represent
 * a list of {@code BookForLibrarianDto} objects in a tabular format.
 * <p>
 * The table includes the following columns:
 * 1. Title
 * 2. Author
 * 3. Publisher
 * 4. Publication Year
 * 5. ISBN
 * 6. Total Copies (All Copies N)
 * 7. Available Copies (Available Copies N)
 */
public class BookForLibrarianTableModel extends AbstractTableModel {

    /**
     * Column names for the table.
     */
    private final String[] columnNames = {"Title", "Author", "Publisher", "Publication Year",
            "isbn", "All Copies N", "Available Copies N"};

    /**
     * List of books displayed in the table.
     */
    private List<BookForLibrarianDto> books;

    /**
     * Constructor to initialize the table model with a list of books.
     *
     * @param books the initial list of {@code BookForLibrarianDto} objects to display.
     */
    public BookForLibrarianTableModel(List<BookForLibrarianDto> books) {
        this.books = books;
    }

    /**
     * Returns the number of rows in the table.
     * This corresponds to the number of books in the list.
     *
     * @return the number of rows (books).
     */
    @Override
    public int getRowCount() {
        return books.size();
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
        BookForLibrarianDto book = books.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> book.getTitle();
            case 1 -> book.getAuthor();
            case 2 -> book.getPublisherName();
            case 3 -> book.getPublicationYear();
            case 4 -> book.getIsbn();
            case 5 -> book.getAllCopiesCount();
            case 6 -> book.getAvailableCopiesCount();
            default -> null;
        };
    }

    /**
     * Updates the list of books in the model and refreshes the table view.
     *
     * @param books the new list of {@code BookForLibrarianDto} objects.
     */
    public void addBooks(List<BookForLibrarianDto> books) {
        this.books = books;
        fireTableDataChanged();
    }
}
