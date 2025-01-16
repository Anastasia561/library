package library.view.table_model;

import library.dto.BookForLibrarianDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookForLibrarianTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Author", "Publisher", "Publication Year",
            "isbn", "All Copies N", "Available Copies N"};
    private List<BookForLibrarianDto> books;

    public BookForLibrarianTableModel(List<BookForLibrarianDto> books) {
        this.books = books;
    }

    @Override
    public int getRowCount() {
        return books.size();
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

    public void addBooks(List<BookForLibrarianDto> books) {
        this.books = books;
        fireTableDataChanged();
    }
}
