package library.mapper;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.entity.Book;
import library.entity.Publisher;
import library.service.PublisherService;

/**
 * The {@code BookMapper} class provides utility methods to map between {@code Book} entities
 * and their corresponding DTOs ({@code BookForLibrarianDto} and {@code BookForUserDto}).
 */
public class BookMapper {
    /**
     * Converts a {@code Book} entity to a {@code BookForLibrarianDto}.
     *
     * @param book the {@code Book} entity to be converted
     * @return a {@code BookForLibrarianDto} containing the book's details
     */
    public static BookForLibrarianDto toForLibrarianDto(Book book) {
        BookForLibrarianDto dto = BookForLibrarianDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .publisherName(book.getPublisher().getName())
                .isbn(book.getIsbn())
                .build();

        if (book.getCopies() != null) {
            long availableCopiesCount = book.getCopies().stream()
                    .map(e -> e.getStatus()
                            .equalsIgnoreCase("available")).count();
            dto.setAllCopiesCount((long) book.getCopies().size());
            dto.setAvailableCopiesCount(availableCopiesCount);
        }
        return dto;
    }

    /**
     * Converts a {@code BookForLibrarianDto} to a {@code Book} entity.
     *
     * @param dto the {@code BookForLibrarianDto} to be converted
     * @return a {@code Book} entity corresponding to the provided DTO
     */
    public static Book toBookFromForLibrarianDto(BookForLibrarianDto dto) {
        PublisherService publisherService = PublisherService.getInstance("hibernate.cfg.xml");
        Publisher publisher = publisherService.getPublisherByName(dto.getPublisherName());
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(publisher)
                .publicationYear(dto.getPublicationYear())
                .isbn(dto.getIsbn())
                .build();
        if (dto.getId() != null) {
            book.setId(dto.getId());
        }
        return book;
    }

    /**
     * Converts a {@code Book} entity to a {@code BookForUserDto}.
     *
     * @param book the {@code Book} entity to be converted
     * @return a {@code BookForUserDto} containing the book's basic details
     */

    public static BookForUserDto toForUserDto(Book book) {
        return BookForUserDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
