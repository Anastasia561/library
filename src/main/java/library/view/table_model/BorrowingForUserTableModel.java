package library.view.table_model;

import library.dto.BorrowingDto;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

public class BorrowingForUserTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Author", "Copy Number", "Borrow Date", "Return Date"};
    private List<BorrowingDto> borrowings;

    public BorrowingForUserTableModel(List<BorrowingDto> borrowings) {
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
