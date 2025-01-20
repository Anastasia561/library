package library.service;

import library.dao.AbstractBaseTest;
import library.dto.UserForLibrarianDto;
import library.entity.Librarian;
import library.entity.User;
import library.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LibrarianServiceTest extends AbstractBaseTest {
    private final LibrarianService librarianService = LibrarianService.getInstance("h2.cfg.xml");
    private final UserService userService = UserService.getInstance("h2.cfg.xml");

    @Test
    void createLibrarianTestShouldThrowDateException() {
        UserForLibrarianDto userDto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test")
                .isLibrarian(true)
                .build();
        userService.createUser(userDto);
        User user = UserMapper.toUserFromUserForLibrarianDto(
                userService.getUserByEmailForLibrarian("test@test"), true);
        Librarian librarian = Librarian.builder()
                .user(user)
                .employmentDate(LocalDate.of(2027, 2, 1))
                .position("Librarian")
                .build();
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> librarianService.createLibrarian(librarian));

        int actual = librarianService.getAll().size();
        int expected = 3;
        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals("Employment date is in the future", e.getMessage())
        );
    }

    @Test
    void createLibrarianTest() {
        UserForLibrarianDto userDto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test")
                .isLibrarian(true)
                .build();
        userService.createUser(userDto);
        User user = UserMapper.toUserFromUserForLibrarianDto(
                userService.getUserByEmailForLibrarian("test@test"), true);
        Librarian librarian = Librarian.builder()
                .user(user)
                .employmentDate(LocalDate.of(2024, 2, 1))
                .position("Librarian")
                .build();
        librarianService.createLibrarian(librarian);
        Librarian createdL = librarianService.getLibrarianByEmail("test@test");
        assertAll(
                () -> assertEquals("Test", createdL.getUser().getName()),
                () -> assertEquals("test@test", createdL.getUser().getEmail()),
                () -> assertEquals("2024-02-01", createdL.getEmploymentDate().toString()),
                () -> assertEquals("Librarian", createdL.getPosition())
        );
    }

    @Test
    void getLibrarianByIdTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> librarianService.getLibrarianById(10));
        assertEquals("Librarian does not exists", e.getMessage());
    }

    @Test
    void getLibrarianByEmailTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> librarianService.getLibrarianByEmail("tt@tt"));
        assertEquals("Librarian does not exists", e.getMessage());
    }

    @Test
    void getLibrarianByIdTest() {
        Librarian librarian = librarianService.getLibrarianById(1);
        assertAll(
                () -> assertEquals("Alice Johnson", librarian.getUser().getName()),
                () -> assertEquals("alice.johnson@example.com", librarian.getUser().getEmail()),
                () -> assertEquals("2020-06-01", librarian.getEmploymentDate().toString()),
                () -> assertEquals("Senior Librarian", librarian.getPosition())
        );
    }

    @Test
    void getLibrarianByEmailTest() {
        Librarian librarian = librarianService.getLibrarianByEmail("alice.johnson@example.com");
        assertAll(
                () -> assertEquals("Alice Johnson", librarian.getUser().getName()),
                () -> assertEquals("alice.johnson@example.com", librarian.getUser().getEmail()),
                () -> assertEquals("2020-06-01", librarian.getEmploymentDate().toString()),
                () -> assertEquals("Senior Librarian", librarian.getPosition())
        );
    }

    @Test
    void updateLibrarian() {
        Librarian librarian = librarianService.getLibrarianById(1);
        librarian.setEmploymentDate(LocalDate.of(2014, 2, 1));
        librarianService.updateLibrarian(librarian);
        Librarian updatedL = librarianService.getLibrarianById(1);
        assertAll(
                () -> assertEquals("Alice Johnson", updatedL.getUser().getName()),
                () -> assertEquals("alice.johnson@example.com", updatedL.getUser().getEmail()),
                () -> assertEquals("2014-02-01", updatedL.getEmploymentDate().toString()),
                () -> assertEquals("Senior Librarian", updatedL.getPosition())
        );
    }

    @Test
    void updateLibrarianShouldThrowDateException() {
        Librarian librarian = librarianService.getLibrarianById(1);
        librarian.setEmploymentDate(LocalDate.of(2026, 2, 1));
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> librarianService.updateLibrarian(librarian));
        assertEquals("Employment date is in the future", e.getMessage());
    }

    @Test
    void getAllTest() {
        int actual = librarianService.getAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void deleteLibrarianByIdShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> librarianService.deleteLibrarianById(1));
        assertEquals("User has connections with other entities, can not be deleted",
                e.getMessage());
    }

    @Test
    void deleteLibrarianById() {
        UserForLibrarianDto userDto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test")
                .isLibrarian(true)
                .build();
        userService.createUser(userDto);
        User user = UserMapper.toUserFromUserForLibrarianDto(
                userService.getUserByEmailForLibrarian("test@test"), true);
        Librarian librarian = Librarian.builder()
                .user(user)
                .employmentDate(LocalDate.of(2024, 2, 1))
                .position("Librarian")
                .build();
        librarianService.createLibrarian(librarian);

        librarianService.deleteLibrarianById(4);
        int actual = librarianService.getAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }
}
