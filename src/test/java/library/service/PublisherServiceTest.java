package library.service;

import library.entity.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublisherServiceTest extends AbstractBaseServiceTest {
    private final PublisherService publisherService = PublisherService.getInstance("h2.cfg.xml");

    @Test
    void createPublisherTest() {
        Publisher publisher = Publisher.builder()
                .name("Test")
                .address("test address")
                .phoneNumber("9-9-9")
                .build();
        publisherService.createPublisher(publisher);
        Publisher savedP = publisherService.getPublisherById(4);
        assertAll(
                () -> assertEquals("Test", savedP.getName()),
                () -> assertEquals("test address", savedP.getAddress()),
                () -> assertEquals("9-9-9", savedP.getPhoneNumber())
        );
    }

    @Test
    void getPublisherByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> publisherService.getPublisherById(10));
        assertEquals("Publisher does not exists", e.getMessage());
    }

    @Test
    void getPublisherByNameTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> publisherService.getPublisherByName("Test"));
        assertEquals("Publisher does not exists", e.getMessage());
    }

    @Test
    void getPublisherByIdTest() {
        Publisher publisher = publisherService.getPublisherById(1);
        assertAll(
                () -> assertEquals("Penguin Books", publisher.getName()),
                () -> assertEquals("123 Penguin St, New York", publisher.getAddress()),
                () -> assertEquals("123-456-7890", publisher.getPhoneNumber())
        );
    }

    @Test
    void getPublisherByNameTest() {
        Publisher publisher = publisherService.getPublisherByName("Penguin Books");
        assertAll(
                () -> assertEquals("Penguin Books", publisher.getName()),
                () -> assertEquals("123 Penguin St, New York", publisher.getAddress()),
                () -> assertEquals("123-456-7890", publisher.getPhoneNumber())
        );
    }

    @Test
    void updatePublisherTest() {
        Publisher publisher = publisherService.getPublisherById(1);
        publisher.setAddress("125 Penguin St, New York");
        publisherService.updatePublisher(publisher);
        Publisher updatedP = publisherService.getPublisherById(1);
        assertAll(
                () -> assertEquals("Penguin Books", updatedP.getName()),
                () -> assertEquals("125 Penguin St, New York", updatedP.getAddress()),
                () -> assertEquals("123-456-7890", updatedP.getPhoneNumber())
        );
    }

    @Test
    void getAllTest() {
        int actual = publisherService.getAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void deletePublisherByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> publisherService.deletePublisherById(1));
        assertEquals("Publisher has connections with other entities, can not be deleted",
                e.getMessage());
    }

    @Test
    void deletePublisherByIdTest() {
        Publisher publisher = Publisher.builder()
                .name("Test")
                .address("test address")
                .phoneNumber("9-9-9")
                .build();
        publisherService.createPublisher(publisher);
        publisherService.deletePublisherById(4);
        int actual = publisherService.getAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }
}
