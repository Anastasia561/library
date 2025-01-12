package library;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.dto.BorrowingDto;
import library.entity.Book;
import library.entity.Publisher;
import library.service.BookService;
import library.service.PublisherService;
import library.service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        BookService bookService = new BookService();

        BookForLibrarianDto dto = BookForLibrarianDto.builder()
                .title("Test")
                .author("author test")
                .publisherName("Penguin Books")
                .publicationYear(2024)
                .isbn("99999").build();

        bookService.createBook(dto);
    }
}
