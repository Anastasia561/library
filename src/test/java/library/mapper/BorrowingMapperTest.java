package library.mapper;

import library.dto.BorrowingDto;
import library.entity.Book;
import library.entity.Borrowing;
import library.entity.Copy;
import library.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowingMapperTest {
    @Test
    void toBorrowingDtoTest() {
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890345")
                .build();

        Copy copy = Copy.builder()
                .copyNumber(1)
                .book(book)
                .build();

        User user = User.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        Borrowing borrowing = Borrowing.builder()
                .id(1)
                .user(user)
                .copy(copy)
                .borrowDate(LocalDate.of(2023, 1, 1))
                .returnDate(LocalDate.of(2023, 1, 10))
                .build();

        BorrowingDto dto = BorrowingMapper.toBorrowingDto(borrowing);

        assertAll(
                () -> assertEquals(1, dto.getId()),
                () -> assertEquals("John Doe", dto.getUserName()),
                () -> assertEquals("johndoe@example.com", dto.getUserEmail()),
                () -> assertEquals(1, dto.getCopyNumber()),
                () -> assertEquals("Test Book", dto.getBookTitle()),
                () -> assertEquals("Test Author", dto.getAuthor()),
                () -> assertEquals("1234567890345", dto.getIsbn()),
                () -> assertEquals(LocalDate.of(2023, 1, 1),
                        dto.getBorrowDate()),
                () -> assertEquals(LocalDate.of(2023, 1, 10),
                        dto.getReturnDate())
        );
    }

    @Test
    void toBorrowingTest() {
        BorrowingDto dto = BorrowingDto.builder()
                .id(1)
                .userEmail("alice.johnson@example.com")
                .copyNumber(1)
                .borrowDate(LocalDate.of(2023, 1, 1))
                .returnDate(LocalDate.of(2023, 1, 10))
                .build();

        Borrowing borrowing = BorrowingMapper.toBorrowing(dto);

        assertAll(
                () -> assertEquals(1, borrowing.getId()),
                () -> assertEquals("Alice Johnson", borrowing.getUser().getName()),
                () -> assertEquals(1, borrowing.getCopy().getCopyNumber()),
                () -> assertEquals(LocalDate.of(2023, 1, 1), borrowing.getBorrowDate()),
                () -> assertEquals(LocalDate.of(2023, 1, 10), borrowing.getReturnDate())
        );
    }
}
