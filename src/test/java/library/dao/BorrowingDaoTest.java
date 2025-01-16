package library.dao;

import library.entity.Borrowing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowingDaoTest extends AbstractBaseTest {
    @Test
    void saveTest() {
        Borrowing borrowing = Borrowing.builder()
                .user(userDao.getById(1))
                .copy(copyDao.getById(1))
                .borrowDate(LocalDate.now())
                .returnDate(null)
                .build();
        borrowingDao.save(borrowing);
        int actual = borrowingDao.findAll().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        String actual = borrowingDao.getById(1).getBorrowDate().toString();
        String expected = "2025-01-01";
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = borrowingDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Borrowing borrowing = borrowingDao.getById(1);
        borrowing.setBorrowDate(LocalDate.now());
        borrowingDao.update(borrowing);
        LocalDate actual = borrowingDao.getById(1).getBorrowDate();
        LocalDate expected = LocalDate.now();
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        borrowingDao.delete(borrowingDao.getById(1));
        int actual = borrowingDao.findAll().size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}
