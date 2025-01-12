package library.mapper;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.entity.Book;
import library.entity.Publisher;
import library.service.PublisherService;

public class BookMapper {
    public static BookForLibrarianDto toForLibrarianDto(Book book) {
        long availableCopiesCount = book.getCopies().stream()
                .map(e -> e.getStatus()
                        .equalsIgnoreCase("available")).count();
        return BookForLibrarianDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisherName(book.getPublisher().getName())
                .isbn(book.getIsbn())
                .allCopies((long) book.getCopies().size())
                .availableCopies(availableCopiesCount).build();
    }

    public static Book toBookFromForLibrarianDto(BookForLibrarianDto dto) {
        PublisherService publisherService = new PublisherService();
        Publisher publisher = publisherService.getPublisherByName(dto.getPublisherName());
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(publisher)
                .publicationYear(dto.getPublicationYear())
                .isbn(dto.getIsbn())
                .build();
    }

    public static BookForUserDto toForUserDto(Book book) {
        return BookForUserDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
