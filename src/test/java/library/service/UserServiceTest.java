package library.service;

import library.dto.UserForLibrarianDto;
import library.dto.UserInfoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest extends AbstractBaseServiceTest {
    private final UserService userService = new UserService("h2.cfg.xml");

    @Test
    void createUserTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false).build();

        userService.createUser(dto);

        UserForLibrarianDto user = userService.getUserByEmailForLibrarian("test@test");
        assertAll(
                () -> assertEquals("Test", user.getName()),
                () -> assertEquals("test@test", user.getEmail()),
                () -> assertEquals("test address", user.getAddress()),
                () -> assertEquals("9-9-9", user.getPhoneNumber()),
                () -> assertFalse(user.getIsLibrarian()),
                () -> assertEquals(0, user.getBorrowings().size())
        );
    }

    @Test
    void createUserTestShouldThrowExceptionForNullEmail() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email(null)
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false).build();

        assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
    }

    @Test
    void createUserTestShouldThrowNotUniqueEmailException() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("alice.johnson@example.com")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false).build();

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
        assertEquals("Email is not unique", e.getMessage());
    }

    @Test
    void createUserTestShouldThrowWrongEmailFormatException() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false).build();

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
        assertEquals("Invalid email format", e.getMessage());
    }

    @Test
    void getUserByIdForLibrarianTest() {
        UserForLibrarianDto dto = userService.getUserByIdForLibrarian(1);
        assertAll(
                () -> assertEquals("Alice Johnson", dto.getName()),
                () -> assertEquals("alice.johnson@example.com", dto.getEmail()),
                () -> assertEquals("555-111-2222", dto.getPhoneNumber()),
                () -> assertEquals("321 Elm St", dto.getAddress()),
                () -> assertTrue(dto.getIsLibrarian()),
                () -> assertEquals(1, dto.getBorrowings().size())
        );
    }

    @Test
    void getUserByIdForLibrarianTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.getUserByIdForLibrarian(10));
        assertEquals("User does not exist", e.getMessage());
    }

    @Test
    void getUserByIdInfoTest() {
        UserInfoDto dto = userService.getUserByIdInfo(1);
        assertAll(
                () -> assertEquals("Alice Johnson", dto.getName()),
                () -> assertEquals("alice.johnson@example.com", dto.getEmail()),
                () -> assertEquals(1, dto.getBorrowings().size())
        );
    }

    @Test
    void getUserByIdInfoTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.getUserByIdInfo(10));
        assertEquals("User does not exist", e.getMessage());
    }

    @Test
    void getUserByEmailForLibrarianTest() {
        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian("alice.johnson@example.com");
        assertAll(
                () -> assertEquals("Alice Johnson", dto.getName()),
                () -> assertEquals("alice.johnson@example.com", dto.getEmail()),
                () -> assertEquals("555-111-2222", dto.getPhoneNumber()),
                () -> assertEquals("321 Elm St", dto.getAddress()),
                () -> assertTrue(dto.getIsLibrarian()),
                () -> assertEquals(1, dto.getBorrowings().size())
        );
    }

    @Test
    void getUserByEmailForLibrarianTestShouldThrowException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.getUserByEmailForLibrarian("test@t"));
        assertEquals("User does not exist", e.getMessage());
    }

    @Test
    void updateUserTest() {
        UserForLibrarianDto user = userService.getUserByIdForLibrarian(1);
        user.setName("Test");
        userService.updateUser(user);
        UserForLibrarianDto dto = userService.getUserByIdForLibrarian(1);
        assertAll(
                () -> assertEquals("Test", dto.getName()),
                () -> assertEquals("alice.johnson@example.com", dto.getEmail()),
                () -> assertEquals("555-111-2222", dto.getPhoneNumber()),
                () -> assertEquals("321 Elm St", dto.getAddress()),
                () -> assertTrue(dto.getIsLibrarian()),
                () -> assertEquals(1, dto.getBorrowings().size())
        );
    }

    @Test
    void getAllForLibrarianTest() {
        int actual = userService.getAllForLibrarian().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void deleteUserByIdTestShouldThrowRelationshipException() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> userService.deleteUserById(1));
        assertEquals("User has connections with other entities, can not be deleted",
                e.getMessage());
    }

    @Test
    void deleteUserByIdTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name("Test")
                .email("test@test")
                .phoneNumber("9-9-9")
                .address("test address")
                .isLibrarian(false).build();

        userService.createUser(dto);
        userService.deleteUserById(4);
        int actual = userService.getAllForLibrarian().size();
        int expected = 3;
        assertEquals(expected, actual);
    }
}
