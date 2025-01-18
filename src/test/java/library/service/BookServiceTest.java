package library.service;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceTest extends AbstractBaseServiceTest {
    private final BookService bookService = BookService.getInstance("h2.cfg.xml");

    @Test
    void createBookTest() {
        BookForLibrarianDto dto = BookForLibrarianDto.builder()
                .title("Test")
                .author("test")
                .publisherName("Penguin Books")
                .publicationYear(2024)
                .isbn("9780061120089")
                .allCopiesCount(4L)
                .availableCopiesCount(3L)
                .build();
        bookService.createBook(dto);
        int actual = bookService.getAllForLibrarian().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void createBookTestShouldThrowIsbnNullException() {
        BookForLibrarianDto dto = BookForLibrarianDto.builder()
                .title("Test")
                .author("test")
                .publisherName("Penguin Books")
                .publicationYear(2024)
                .isbn(null)
                .allCopiesCount(4L)
                .availableCopiesCount(3L)
                .build();
        assertThrows(RuntimeException.class,
                () -> bookService.createBook(dto));
    }

    @Test
    void getBookByIsbnForLibrarianTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookService.getBookByIsbnForLibrarian("9780743273500"));
        assertEquals("Book does not exists", e.getMessage());
    }

    @Test
    void getBookByIsbnForLibrarianTest() {
        BookForLibrarianDto dto = bookService.getBookByIsbnForLibrarian("9780743273565");
        assertAll(
                () -> assertEquals("The Great Gatsby", dto.getTitle()),
                () -> assertEquals("F. Scott Fitzgerald", dto.getAuthor()),
                () -> assertEquals("Penguin Books", dto.getPublisherName()),
                () -> assertEquals(1925, dto.getPublicationYear()),
                () -> assertEquals("9780743273565", dto.getIsbn()),
                () -> assertEquals(2, dto.getAllCopiesCount()),
                () -> assertEquals(2, dto.getAvailableCopiesCount())
        );

    }

    @Test
    void getBookByIdForLibrarianTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookService.getBookByIdForLibrarian(10));
        assertEquals("Book does not exists", e.getMessage());
    }

    @Test
    void getBookByIdForLibrarianTest() {
        BookForLibrarianDto dto = bookService.getBookByIdForLibrarian(1);
        assertAll(
                () -> assertEquals("The Great Gatsby", dto.getTitle()),
                () -> assertEquals("F. Scott Fitzgerald", dto.getAuthor()),
                () -> assertEquals("Penguin Books", dto.getPublisherName()),
                () -> assertEquals(1925, dto.getPublicationYear()),
                () -> assertEquals("9780743273565", dto.getIsbn()),
                () -> assertEquals(2, dto.getAllCopiesCount()),
                () -> assertEquals(2, dto.getAvailableCopiesCount())
        );
    }

    @Test
    void getBookByIdForUserTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookService.getBookByIdForUser(10));
        assertEquals("Book does not exists", e.getMessage());
    }

    @Test
    void getBookByIdForUserTest() {
        BookForUserDto dto = bookService.getBookByIdForUser(1);
        assertAll(
                () -> assertEquals("The Great Gatsby", dto.getTitle()),
                () -> assertEquals("F. Scott Fitzgerald", dto.getAuthor())
        );
    }

    @Test
    void updateBookTest() {
        BookForLibrarianDto dto = bookService.getBookByIdForLibrarian(1);
        dto.setTitle("Test");
        bookService.updateBook(dto);
        BookForLibrarianDto updatedDto = bookService.getBookByIdForLibrarian(1);
        assertAll(
                () -> assertEquals("Test", updatedDto.getTitle()),
                () -> assertEquals("F. Scott Fitzgerald", updatedDto.getAuthor()),
                () -> assertEquals("Penguin Books", updatedDto.getPublisherName()),
                () -> assertEquals(1925, updatedDto.getPublicationYear()),
                () -> assertEquals("9780743273565", updatedDto.getIsbn()),
                () -> assertEquals(2, updatedDto.getAllCopiesCount()),
                () -> assertEquals(2, updatedDto.getAvailableCopiesCount())
        );
    }

    @Test
    void getAllForLibrarianTest() {
        int actual = bookService.getAllForLibrarian().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getAllForUserTest() {
        int actual = bookService.getAllForUser().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getAllAvailableForUserTest() {
        int actual = bookService.getAllAvailableForUser().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdTestShouldThrowRelationshipException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookService.deleteBookById(1));
        assertEquals("Book has connections with other entities, can not be deleted",
                e.getMessage());
    }

    @Test
    void deleteByIdTest() {
        BookForLibrarianDto dto = BookForLibrarianDto.builder()
                .title("Test")
                .author("test")
                .publisherName("Penguin Books")
                .publicationYear(2024)
                .isbn("9780061120089")
                .allCopiesCount(4L)
                .availableCopiesCount(3L)
                .build();
        bookService.createBook(dto);
        bookService.deleteBookById(4);
        assertEquals(3, bookService.getAllForUser().size());
    }
}
