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

    public void createUser(UserForLibrarianDto dto) {
        userService.createUser(dto);
    }

    public void createLibrarian(UserForLibrarianDto dto, LocalDate employmentDate, String position) {
        if (employmentDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Employment date is in the future");
        } else {
            createUser(dto);

            UserForLibrarianDto userDto = userService.getUserByEmailForLibrarian(dto.getEmail());
            User user = UserMapper.toUserFromUserForLibrarianDto(userDto, true);

            Librarian librarian = Librarian.builder()
                    .employmentDate(employmentDate)
                    .position(position)
                    .user(user)
                    .build();

            librarianService.createLibrarian(librarian);
            user.setLibrarian(librarian);
            userService.updateUser(UserMapper.toUserForLibrarianDto(user));
        }
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
            librarian = librarianService.getLibrarianById(librarian.getId());
            librarianService.deleteLibrarianById(librarian.getId());
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
        UserForLibrarianDto dto2 = userService.getUserByEmailForLibrarian(dto.getEmail());
        dto.setId(dto2.getId());
        userService.updateUser(dto);
    }

    public void updateLibrarian(UserForLibrarianDto dto, String position, LocalDate empDate) {
        updateUser(dto);
        Librarian librarian = getLibrarianByEmail(dto.getEmail());
        librarian.setPosition(position);
        librarian.setEmploymentDate(empDate);
        librarianService.updateLibrarian(librarian);
    }

    public void updateBook(BookForLibrarianDto dto) {
        BookForLibrarianDto bookDto = bookService.getBookByIsbnForLibrarian(dto.getIsbn());
        dto.setId(bookDto.getId());
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

    public Librarian getLibrarianByEmail(String email) {
        return librarianService.getLibrarianByEmail(email);
    }
}
