package library.dao;

import library.entity.Copy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CopyDaoTest extends AbstractBaseTest {
    @Test
    void saveTest() {
        Copy copy = Copy.builder()
                .book(bookDao.getById(1))
                .copyNumber(20)
                .status("Available")
                .build();
        copyDao.save(copy);
        int actual = copyDao.findAll().size();
        int expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        int actual = copyDao.getById(1).getCopyNumber();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = copyDao.findAll().size();
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Copy copy = copyDao.getById(1);
        copy.setCopyNumber(100);
        copyDao.update(copy);
        int actual = copyDao.getById(1).getCopyNumber();
        int expected = 100;
        assertEquals(expected, actual);
    }

    @Test
    void deleteTestShouldThrowException() {
        assertThrows(RuntimeException.class, () -> copyDao.delete(copyDao.getById(1)));
    }

    @Test
    void deleteTest() {
        Copy copy = Copy.builder()
                .book(bookDao.getById(1))
                .copyNumber(20)
                .status("Available")
                .build();
        copyDao.save(copy);
        copyDao.delete(copyDao.getById(7));
        int actual = copyDao.findAll().size();
        int expected = 6;
        assertEquals(expected, actual);
    }
}
