package library;

import library.controller.Controller;
import library.dto.UserForLibrarianDto;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;

import java.time.LocalDate;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.run();
//        Controller controller = new Controller(new UserService(), new LibrarianService(),
//                new BookService(), new BorrowingService());
//        controller.createLibrarian("test", "e@e", "9-9", "dd",
//                LocalDate.now(), "hhh");
//
//        UserForLibrarianDto userForLibrarianDto = controller.getAllUserDto()
//                .stream()
//                .filter(e -> e.getEmail().equals("e@e"))
//                .toList().get(0);
//        System.out.println(userForLibrarianDto);
    }
}
