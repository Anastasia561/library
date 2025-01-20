package library.dao;

import library.entity.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublisherDaoTest extends AbstractBaseTest {
    @Test
    void saveTest() {
        Publisher publisher = Publisher.builder()
                .name("Test")
                .address("test")
                .phoneNumber("9-9-0")
                .build();
        publisherDao.save(publisher);
        int actual = publisherDao.findAll().size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        String actual = publisherDao.getById(1).getName();
        String expected = "Penguin Books";
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        int actual = publisherDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Publisher publisher = publisherDao.getById(1);
        publisher.setName("Test");
        publisherDao.update(publisher);
        String actual = publisherDao.getById(1).getName();
        String expected = "Test";
        assertEquals(expected, actual);
    }

    @Test
    void deleteTestShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> publisherDao.delete(publisherDao.getById(1)));
    }

    @Test
    void deleteTest() {
        Publisher publisher = Publisher.builder()
                .name("Test")
                .address("test")
                .phoneNumber("9-9-0")
                .build();
        publisherDao.save(publisher);
        publisherDao.delete(publisherDao.getById(4));
        int actual = publisherDao.findAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getByNameTest() {
        Integer actual = publisherDao.getByName("Penguin Books").getId();
        Integer expected = 1;
        assertEquals(expected, actual);
    }
}
