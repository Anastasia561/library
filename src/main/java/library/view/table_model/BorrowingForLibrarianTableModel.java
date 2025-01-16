package library.view.table_model;

import library.dto.BorrowingDto;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

public class BorrowingForLibrarianTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Id", "User Name", "User Email", "Copy Number", "Book Title",
            "Author", "isbn", "Borrow Date", "Return Date"};
    private List<BorrowingDto> borrowings;

    public BorrowingForLibrarianTableModel(List<BorrowingDto> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public int getRowCount() {
        return borrowings.size();
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

    public void addBorrowings(List<BorrowingDto> borrowings) {
        this.borrowings = borrowings;
        fireTableDataChanged();
    }
}
