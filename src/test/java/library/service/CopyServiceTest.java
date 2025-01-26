package library.service;

import library.dao.AbstractBaseTest;
import library.entity.Book;
import library.entity.Copy;
import library.mapper.BookMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CopyServiceTest extends AbstractBaseTest {
    private final CopyService copyService = CopyService.getInstance("h2.cfg.xml");
    private final BookService bookService = BookService.getInstance("h2.cfg.xml");

    @Test
    void createCopyTest() {
        Book book = BookMapper.toBookFromForLibrarianDto(bookService.getBookByIsbnForLibrarian("9780743273565"));

        Copy copy = Copy.builder()
                .book(book)
                .copyNumber(20)
                .status("Available")
                .build();
        copyService.createCopy(copy);
        int actual = copyService.getAll().size();
        int expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void getCopyByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> copyService.getCopyById(10));
        assertEquals("Copy does not exists", e.getMessage());
    }

    @Test
    void getCopyByIdTest() {
        Copy copy = copyService.getCopyById(1);
        assertAll(
                () -> assertEquals("9780743273565", copy.getBook().getIsbn()),
                () -> assertEquals(1, copy.getCopyNumber()),
                () -> assertEquals("Available", copy.getStatus())
        );
    }

    @Test
    void getCopyByCopyNumberTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> copyService.getCopyByCopyNumber(10));
        assertEquals("Copy does not exists", e.getMessage());
    }

    @Test
    void getCopyByCopyNumberTest() {
        Copy copy = copyService.getCopyByCopyNumber(1);
        assertAll(
                () -> assertEquals("9780743273565", copy.getBook().getIsbn()),
                () -> assertEquals(1, copy.getCopyNumber()),
                () -> assertEquals("Available", copy.getStatus())
        );
    }

    @Test
    void updateCopyTest() {
        Copy copy = copyService.getCopyById(1);
        copy.setStatus("Borrowed");
        copyService.updateCopy(copy);
        Copy updatedCopy = copyService.getCopyById(1);
        assertAll(
                () -> assertEquals("9780743273565", updatedCopy.getBook().getIsbn()),
                () -> assertEquals(1, updatedCopy.getCopyNumber()),
                () -> assertEquals("Borrowed", updatedCopy.getStatus())
        );
    }

    @Test
    void getAllTest() {
        int actual = copyService.getAll().size();
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void deleteCopyByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> copyService.deleteCopyById(1));
        assertEquals("Copy has connections with other entities, can not be deleted", e.getMessage());
    }

    @Test
    void deleteCopyByIdTest() {
        Book book = BookMapper.toBookFromForLibrarianDto(bookService.getBookByIsbnForLibrarian("9780743273565"));

        Copy copy = Copy.builder()
                .book(book)
                .copyNumber(21)
                .status("Available")
                .build();
        copyService.createCopy(copy);
        copyService.deleteCopyById(7);
        int actual = copyService.getAll().size();
        int expected = 6;
        assertEquals(expected, actual);
    }
}
