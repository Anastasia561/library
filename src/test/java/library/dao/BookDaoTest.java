package library.dao;

import library.entity.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookDaoTest extends AbstractBaseDaoTest {
    @Test
    void getBookByIdTest() {
        String actual = bookDao.getById(1).getIsbn();
        String expected = "9780743273565";
        assertEquals(expected, actual);
    }

    @Test
    void saveTest() {
        Book book = Book.builder()
                .title("Test")
                .author("Test author")
                .publisher(publisherDao.getById(1))
                .publicationYear(2020)
                .isbn("9780743278565")
                .build();
        bookDao.save(book);
        String actual = bookDao.getById(4).getTitle();
        String expected = "Test";
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = bookDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Book book = bookDao.getById(1);
        book.setTitle("Test");
        bookDao.update(book);
        String actual = bookDao.getById(1).getTitle();
        String expected = "Test";
        assertEquals(expected, actual);
    }

    @Test
    void deleteTestShouldThrowException() {
        Book book = bookDao.getById(1);
        assertThrows(RuntimeException.class, () -> bookDao.delete(book));
    }

    @Test
    void deleteTest() {
        Book book = Book.builder()
                .title("Test")
                .author("Test author")
                .publisher(publisherDao.getById(1))
                .publicationYear(2020)
                .isbn("9780743278565")
                .build();
        bookDao.save(book);
        bookDao.delete(bookDao.getById(4));
        Book actual = bookDao.getById(4);
        assertNull(actual);
    }

    @Test
    void getByIsbnTest() {
        String actual = bookDao.getByIsbn("9780743273565").getTitle();
        String expected = "The Great Gatsby";
        assertEquals(expected, actual);
    }
}
