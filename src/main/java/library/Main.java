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

        PublisherService publisherService = new PublisherService();

        Publisher publisher = publisherService.getPublisherByName("test");

    }
}