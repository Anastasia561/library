package library.view.table_model;

import library.dto.BookForUserDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Author"};
    private List<BookForUserDto> books;

    public BookTableModel(List<BookForUserDto> books) {
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
        BookForUserDto book = books.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> book.getTitle();
            case 1 -> book.getAuthor();
            default -> null;
        };
    }
}
