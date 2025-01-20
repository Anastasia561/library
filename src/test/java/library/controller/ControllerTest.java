package library.controller;

import library.dao.AbstractBaseTest;
import library.dto.UserForLibrarianDto;
import library.entity.Librarian;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerTest extends AbstractBaseTest {
    private final static String CONF = "h2.cfg.xml";
    private final Controller controller = new Controller(UserService.getInstance(CONF),
            LibrarianService.getInstance(CONF),
            BookService.getInstance(CONF),
            BorrowingService.getInstance(CONF));

    @Test
    void createUserTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false)
                .build();
        controller.createUser(dto);

        int actual = controller.getAllUserDto().size();
        int expected = 4;

        UserForLibrarianDto user = controller.getUserForLibrarianDtoByEmail("test@test");
        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals("Test", user.getName()),
                () -> assertEquals("test@test", user.getEmail()),
                () -> assertEquals("9-9-9", user.getPhoneNumber()),
                () -> assertEquals("test address", user.getAddress()),
                () -> assertFalse(user.getIsLibrarian())
        );
    }

    @Test
    void createLibrarianTestShouldThrowDateException() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(true)
                .build();

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> controller.createLibrarian(dto, LocalDate.now().plusYears(2), "p"));
        assertEquals("Employment date is in the future", e.getMessage());
    }

    @Test
    void createLibrarianTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(true)
                .build();

        controller.createLibrarian(dto, LocalDate.of(2025, 1, 18), "test pos");

        int actual = controller.getAllUserDto().size();
        int expected = 4;

        UserForLibrarianDto userDto = controller.getUserForLibrarianDtoByEmail("test@test");
        Librarian librarian = controller.getLibrarianByEmail("test@test");
        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals("Test", userDto.getName()),
                () -> assertEquals("test@test", userDto.getEmail()),
                () -> assertEquals("9-9-9", userDto.getPhoneNumber()),
                () -> assertEquals("test address", userDto.getAddress()),
                () -> assertTrue(userDto.getIsLibrarian()),
                () -> assertEquals("test pos", librarian.getPosition()),
                () -> assertEquals("2025-01-18", librarian.getEmploymentDate().toString())
        );
    }

    @Test
    void getAllUserDtoTest() {
        int actual = controller.getAllUserDto().size();
        assertEquals(3, actual);
    }

    @Test
    void getAllBookDtoTest() {
        int actual = controller.getAllBookDto().size();
        assertEquals(3, actual);
    }

    @Test
    void getAllBorrowingDtoTest() {
        int actual = controller.getAllBorrowingDto().size();
        assertEquals(3, actual);
    }

    @Test
    void deleteUserByEmailTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false)
                .build();
        controller.createUser(dto);
        controller.deleteUserByEmail("test@test");
        int actual = controller.getAllUserDto().size();
        assertEquals(3, actual);
    }

    @Test
    void getAllBookForUserDtoTest() {
        int actual = controller.getAllBookForUserDto().size();
        assertEquals(3, actual);
    }

    @Test
    void getAvailableBookForUserDtoTest() {
        int actual = controller.getAvailableBookForUserDto().size();
        assertEquals(3, actual);
    }

    @Test
    void getAllBorrowingForUserByEmailTest() {
        int actual = controller.getAllBorrowingsForUserByEmail("alice.johnson@example.com").size();
        assertEquals(1, actual);
    }

    @Test
    void getUserNameByEmailTest() {
        String actual = controller.getUserNameByEmail("alice.johnson@example.com");
        assertEquals("Alice Johnson", actual);
    }

    @Test
    void checkIfIsLibrarianTest() {
        boolean actual = controller.checkIfIsLibrarian("alice.johnson@example.com");
        assertTrue(actual);
    }

    @Test
    void deleteBookByIsbnShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> controller.deleteBookByIsbn("9780743273565"));
        assertEquals("Book has connections with other entities, can not be deleted", e.getMessage());
    }

    @Test
    void deleteBorrowingByIdTest() {
        controller.deleteBorrowingById(1);
        int actual = controller.getAllBorrowingsForUserByEmail("alice.johnson@example.com").size();
        assertEquals(0, actual);
    }
}
