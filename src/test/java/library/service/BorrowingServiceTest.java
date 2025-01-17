package library.service;

import library.dto.BorrowingDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BorrowingServiceTest extends AbstractBaseServiceTest {
    private final BorrowingService borrowingService = new BorrowingService("h2.cfg.xml");

    @Test
    void createBorrowingTest() {
        BorrowingDto dto = BorrowingDto.builder()
                .userName("Alice Johnson")
                .userEmail("alice.johnson@example.com")
                .copyNumber(1)
                .bookTitle("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .borrowDate(LocalDate.now())
                .build();
        borrowingService.createBorrowing(dto);
        int actual = borrowingService.getAll().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getBorrowingByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> borrowingService.getBorrowingById(10));
        assertEquals("Borrowing does not exists", e.getMessage());
    }

    @Test
    void getBorrowingByIdTest() {
        BorrowingDto dto = borrowingService.getBorrowingById(1);
        assertAll(
                () -> assertEquals("Alice Johnson", dto.getUserName()),
                () -> assertEquals("alice.johnson@example.com", dto.getUserEmail()),
                () -> assertEquals(1, dto.getCopyNumber()),
                () -> assertEquals("The Great Gatsby", dto.getBookTitle()),
                () -> assertEquals("F. Scott Fitzgerald", dto.getAuthor()),
                () -> assertEquals("9780743273565", dto.getIsbn()),
                () -> assertEquals("2025-01-01", dto.getBorrowDate().toString()),
                () -> assertEquals("2025-01-10", dto.getReturnDate().toString())
        );

    }

    @Test
    void updateBorrowingTest() {
        BorrowingDto dto = borrowingService.getBorrowingById(1);
        dto.setBookTitle("Test");
        borrowingService.updateBorrowing(dto);
        BorrowingDto updatedDto = borrowingService.getBorrowingById(1);
        assertAll(
                () -> assertEquals("Alice Johnson", dto.getUserName()),
                () -> assertEquals("alice.johnson@example.com", dto.getUserEmail()),
                () -> assertEquals(1, dto.getCopyNumber()),
                () -> assertEquals("Test", dto.getBookTitle()),
                () -> assertEquals("F. Scott Fitzgerald", dto.getAuthor()),
                () -> assertEquals("9780743273565", dto.getIsbn()),
                () -> assertEquals("2025-01-01", dto.getBorrowDate().toString()),
                () -> assertEquals("2025-01-10", dto.getReturnDate().toString())
        );
    }

    @Test
    void getAllForLibrarianTest() {
        int actual = borrowingService.getAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdTest() {
        borrowingService.deleteBorrowingById(1);
        int actual = borrowingService.getAll().size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}
