package library;

import library.entity.Book;
import library.entity.Publisher;
import library.service.BookService;
import library.service.PublisherService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        PublisherService service = new PublisherService();
        Publisher p = service.getPublisherById(1);
        List<Book> books = p.getBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
