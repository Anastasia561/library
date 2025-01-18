package library.controller;

import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.dto.BorrowingDto;
import library.dto.UserForLibrarianDto;
import library.entity.Librarian;
import library.entity.User;
import library.mapper.UserMapper;
import library.service.BookService;
import library.service.BorrowingService;
import library.service.LibrarianService;
import library.service.UserService;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final UserService userService;
    private final LibrarianService librarianService;
    private final BookService bookService;
    private final BorrowingService borrowingService;

    public Controller(UserService userService, LibrarianService librarianService, BookService bookService,
                      BorrowingService borrowingService) {
        this.userService = userService;
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.borrowingService = borrowingService;

    }

    public void createUser(String name, String email, String phoneNumber,
                           String address, Boolean isLibrarian) {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .isLibrarian(isLibrarian)
                .build();
        userService.createUser(dto);
    }

    public void createLibrarian(String name, String email, String phoneNumber,
                                String address, LocalDate employmentDate, String position) {
        createUser(name, email, phoneNumber, address, true);

        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian(email);
        User user = UserMapper.toUserFromUserForLibrarianDto(dto);

        Librarian librarian = Librarian.builder()
                .employmentDate(employmentDate)
                .position(position)
                .user(user)
                .build();

        librarianService.createLibrarian(librarian);
        dto.setIsLibrarian(true);
        userService.updateUser(dto);
    }

    public List<UserForLibrarianDto> getAllUserDto() {
        return userService.getAllForLibrarian();
    }

    public List<BookForLibrarianDto> getAllBookDto() {
        return bookService.getAllForLibrarian();
    }

    public List<BorrowingDto> getAllBorrowingDto() {
        return borrowingService.getAll();
    }

    public void deleteUserByEmail(String email) {
        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian(email);
        Integer id = dto.getId();
        Boolean isLibrarian = dto.getIsLibrarian();
        if (isLibrarian) {
            Librarian librarian = librarianService.getLibrarianByEmail(email);
//            librarianService.deleteLibrarianById(librarian.getId());
            librarian = librarianService.getLibrarianById(librarian.getId());
            librarianService.deleteLibrarianById(librarian.getId());
            //throw new RuntimeException("Not allowed to delete librarians");
        }
        userService.deleteUserById(id);
    }

    public List<BookForUserDto> getAllBookForUserDto() {
        return bookService.getAllForUser();
    }

    public List<BookForUserDto> getAvailableBookForUserDto() {
        return bookService.getAllAvailableForUser();
    }

    public List<BorrowingDto> getAllBorrowingsForUserByEmail(String email) {
        return borrowingService.getAll().stream()
                .filter(e -> e.getUserEmail().equals(email))
                .toList();
    }

    public String getUserNameByEmail(String email) {
        return userService.getUserByEmailForLibrarian(email).getName();
    }

    public boolean checkIfIsLibrarian(String email) {
        return userService.getUserByEmailForLibrarian(email).getIsLibrarian();
    }

    public void deleteBookByIsbn(String isbn) {
        Integer id = bookService.getBookByIsbnForLibrarian(isbn).getId();
        bookService.deleteBookById(id);
    }

    public void deleteBorrowingById(Integer id) {
        borrowingService.deleteBorrowingById(id);
    }

    public void updateUser(UserForLibrarianDto dto) {
        userService.updateUser(dto);
    }

    public void updateBook(BookForLibrarianDto dto) {
        bookService.updateBook(dto);
    }

    public void updateBorrowing(BorrowingDto dto) {
        borrowingService.updateBorrowing(dto);
    }

    public void createBook(BookForLibrarianDto dto) {
        bookService.createBook(dto);
    }

    public void createBorrowing(BorrowingDto dto) {
        borrowingService.createBorrowing(dto);
    }

    public BookForLibrarianDto getBookForLibrarianDtoByIsbn(String isbn) {
        return bookService.getBookByIsbnForLibrarian(isbn);
    }

    public UserForLibrarianDto getUserForLibrarianDtoByEmail(String email) {
        return userService.getUserByEmailForLibrarian(email);
    }

    public BorrowingDto getBorrowingDtoById(Integer id) {
        return borrowingService.getBorrowingById(id);
    }
}
