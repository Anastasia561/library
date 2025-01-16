package library.dao;

import library.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDaoTest extends AbstractBaseTest {
    @Test
    void saveTest() {
        User user = User.builder()
                .name("Test")
                .email("F@f")
                .phoneNumber("9-9-9")
                .address("test")
                .build();
        userDao.save(user);
        int actual = userDao.findAll().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        String actual = userDao.getById(1).getEmail();
        String expected = "alice.johnson@example.com";
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = userDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        User user = userDao.getById(1);
        user.setName("Test");
        userDao.update(user);
        String actual = userDao.getById(1).getName();
        String expected = "Test";
        assertEquals(expected, actual);
    }

    @Test
    void deleteTestShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> userDao.delete(userDao.getById(1)));
    }

    @Test
    void deleteTest() {
        User user = User.builder()
                .name("Test")
                .email("F@f")
                .phoneNumber("9-9-9")
                .address("test")
                .build();
        userDao.save(user);
        userDao.delete(userDao.getById(4));
        int actual = userDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getByEmailTest() {
        Integer actual = userDao.getByEmail("alice.johnson@example.com").getId();
        Integer expected = 1;
        assertEquals(expected, actual);
    }
}
