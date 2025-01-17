package library.dao;

import library.entity.Librarian;
import library.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibrarianDaoTest extends AbstractBaseDaoTest {
    @Test
    void saveTest() {
        User user = User.builder()
                .name("test")
                .email("F@f")
                .phoneNumber("8-88")
                .address("test")
                .build();
        userDao.save(user);
        Librarian librarian = Librarian.builder()
                .user(userDao.getByEmail("F@f"))
                .employmentDate(LocalDate.now())
                .position("test")
                .build();
        librarianDao.save(librarian);
        int actual = librarianDao.findAll().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        String actual = librarianDao.getById(1).getPosition();
        String expected = "Senior Librarian";
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = librarianDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Librarian librarian = librarianDao.getById(1);
        librarian.setPosition("Test");
        librarianDao.update(librarian);
        String actual = librarianDao.getById(1).getPosition();
        String expected = "Test";
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        User user = User.builder()
                .name("test")
                .email("F@f")
                .phoneNumber("8-88")
                .address("test")
                .build();
        userDao.save(user);
        Librarian librarian = Librarian.builder()
                .user(userDao.getByEmail("F@f"))
                .employmentDate(LocalDate.now())
                .position("test")
                .build();
        librarianDao.save(librarian);
        librarianDao.delete(librarianDao.getById(4));
        int actual = borrowingDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getByEmailTest() {
        Integer actual = librarianDao.getByEmail("alice.johnson@example.com").getId();
        Integer expected = 1;
        assertEquals(expected, actual);
    }
}
