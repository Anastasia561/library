package library.validator;

import library.entity.Book;
import library.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {
    @Test
    void validateTestShouldThrowExceptionOfIncorrectEmailFormat() {
        User user = User.builder()
                .name("Test")
                .email("test")
                .address("test")
                .phoneNumber("9-9-9")
                .build();
        RuntimeException e = assertThrows(RuntimeException.class, () -> Validator.validate(user));
        assertEquals("Invalid email format", e.getMessage());
    }

    @Test
    void validateTestShouldNotThrowExceptionOfIncorrectEmailFormat() {
        User user = User.builder()
                .name("Test")
                .email("test@example.com")
                .address("test")
                .phoneNumber("9-9-9")
                .build();
        assertDoesNotThrow(() -> Validator.validate(user));
    }

    @Test
    void validateTestShouldThrowExceptionOfIncorrectIsbnFormat() {
        Book book = Book.builder()
                .title("Test")
                .author("Test")
                .publicationYear(2020)
                .isbn("956")
                .build();
        RuntimeException e = assertThrows(RuntimeException.class, () -> Validator.validate(book));
        assertEquals("Invalid isbn format", e.getMessage());
    }

    @Test
    void validateTestShouldNotThrowExceptionOfIncorrectIsbnFormat() {
        Book book = Book.builder()
                .title("Test")
                .author("Test")
                .publicationYear(2020)
                .isbn("9780743273565")
                .build();
        assertDoesNotThrow(() -> Validator.validate(book));
    }
}
