package library.mapper;

import library.dao.AbstractBaseTest;
import library.dto.UserForLibrarianDto;
import library.dto.UserInfoDto;
import library.entity.Borrowing;
import library.entity.Librarian;
import library.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest extends AbstractBaseTest {
    @Test
    void toUserInfoDtoTest() {
        User user = User.builder()
                .id(1)
                .name("Test")
                .email("test@example.com")
                .build();

        Borrowing borrowing = Borrowing.builder()
                .user(user)
                .copy(copyDao.getById(1))
                .borrowDate(LocalDate.now())
                .build();

        user.setBorrowings(List.of(borrowing));
        UserInfoDto dto = UserMapper.toUserInfoDto(user);

        assertAll(
                () -> assertEquals(1, dto.getId()),
                () -> assertEquals("Test", dto.getName()),
                () -> assertEquals("test@example.com", dto.getEmail()),
                () -> assertEquals(1, dto.getBorrowings().size())
        );
    }

    @Test
    void toUserForLibrarianDtoTest() {
        User user = User.builder()
                .id(1)
                .name("John Doe")
                .email("johndoe@example.com")
                .phoneNumber("1234567890345")
                .address("123 Main St")
                .librarian(new Librarian())
                .build();

        Borrowing borrowing = Borrowing.builder()
                .user(user)
                .copy(copyDao.getById(1))
                .borrowDate(LocalDate.now())
                .build();

        user.setBorrowings(List.of(borrowing));

        UserForLibrarianDto dto = UserMapper.toUserForLibrarianDto(user);

        assertAll(
                () -> assertEquals(1, dto.getId()),
                () -> assertEquals("John Doe", dto.getName()),
                () -> assertEquals("johndoe@example.com", dto.getEmail()),
                () -> assertEquals("123 Main St", dto.getAddress()),
                () -> assertEquals(1, dto.getBorrowings().size()),
                () -> assertTrue(dto.getIsLibrarian())
        );
    }

    @Test
    void toUserFromUserForLibrarianDtoTest() {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .id(1)
                .name("John Doe")
                .email("johndoe@example.com")
                .phoneNumber("1234567890345")
                .address("123 Main St")
                .isLibrarian(true)
                .build();

        User user = UserMapper.toUserFromUserForLibrarianDto(dto, true);

        assertAll(
                () -> assertEquals(1, user.getId()),
                () -> assertEquals("John Doe", user.getName()),
                () -> assertEquals("johndoe@example.com", user.getEmail()),
                () -> assertEquals("1234567890345", user.getPhoneNumber()),
                () -> assertEquals("123 Main St", user.getAddress()),
                () -> assertNull(user.getBorrowings())
        );
    }
}
