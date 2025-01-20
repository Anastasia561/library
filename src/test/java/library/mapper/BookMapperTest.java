package library.mapper;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.entity.Book;
import library.entity.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookMapperTest {
    @Test
    void toForLibrarianDtoTest() {
        Publisher publisher = Publisher.builder()
                .name("Test Publisher")
                .address("Test address")
                .phoneNumber("9-9-9")
                .build();
        Book book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .publisher(publisher)
                .publicationYear(2020)
                .isbn("1234567890345")
                .build();

        BookForLibrarianDto dto = BookMapper.toForLibrarianDto(book);

        assertAll(
                () -> assertNull(dto.getId()),
                () -> assertEquals("Test Book", dto.getTitle()),
                () -> assertEquals("Test Author", dto.getAuthor()),
                () -> assertEquals(2020, dto.getPublicationYear()),
                () -> assertEquals("Test Publisher", dto.getPublisherName()),
                () -> assertEquals("1234567890345", dto.getIsbn()),
                () -> assertNull(dto.getAllCopiesCount()),
                () -> assertNull(dto.getAvailableCopiesCount())
        );
    }

    @Test
    void toForUserDtoTest() {
        Book book = Book.builder()
                .id(1)
                .title("Test Book")
                .author("Test Author")
                .build();

        BookForUserDto dto = BookMapper.toForUserDto(book);
        assertAll(
                () -> assertEquals(1, dto.getId()),
                () -> assertEquals("Test Book", dto.getTitle()),
                () -> assertEquals("Test Author", dto.getAuthor())
        );
    }
}
