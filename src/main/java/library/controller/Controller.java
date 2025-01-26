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

/**
 * The {@code Controller} class serves as a mediator between the application services
 * and the user interface. It provides methods to perform operations
 * related to users, librarians, books, borrowings.
 */
public class Controller {
    private final UserService userService;
    private final LibrarianService librarianService;
    private final BookService bookService;
    private final BorrowingService borrowingService;

    /**
     * Constructs a {@code Controller} with the specified services.
     *
     * @param userService      the service to manage user-related operations
     * @param librarianService the service to manage librarian-related operations
     * @param bookService      the service to manage book-related operations
     * @param borrowingService the service to manage borrowing-related operations
     */
    public Controller(UserService userService, LibrarianService librarianService,
                      BookService bookService, BorrowingService borrowingService) {
        this.userService = userService;
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.borrowingService = borrowingService;
    }

    /**
     * Creates a new user in the system.
     *
     * @param dto the {@code UserForLibrarianDto} containing user details
     */
    public void createUser(UserForLibrarianDto dto) {
        userService.createUser(dto);
    }

    /**
     * Creates a new librarian in the system.
     *
     * @param dto            the {@code UserForLibrarianDto} containing user details
     * @param employmentDate the employment date of the librarian
     * @param position       the position of the librarian
     * @throws RuntimeException if the employment date is in the future
     */
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

    /**
     * Retrieves all users as a list of {@code UserForLibrarianDto}.
     *
     * @return a list of all users
     */
    public List<UserForLibrarianDto> getAllUserDto() {
        return userService.getAllForLibrarian();
    }

    /**
     * Retrieves all books as a list of {@code BookForLibrarianDto}.
     *
     * @return a list of all books
     */
    public List<BookForLibrarianDto> getAllBookDto() {
        return bookService.getAllForLibrarian();
    }

    /**
     * Retrieves all borrowing records as a list of {@code BorrowingDto}.
     *
     * @return a list of all borrowings
     */
    public List<BorrowingDto> getAllBorrowingDto() {
        return borrowingService.getAll();
    }

    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to delete
     */
    public void deleteUserByEmail(String email) {
        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian(email);
        Integer id = dto.getId();
        Boolean isLibrarian = dto.getIsLibrarian();
        if (isLibrarian) {
            Librarian librarian = librarianService.getLibrarianByEmail(email);
            librarianService.deleteLibrarianById(librarian.getId());
        }
        userService.deleteUserById(id);
    }

    /**
     * Retrieves all available books as a list of {@code BookForUserDto}.
     *
     * @return a list of all available books
     */
    public List<BookForUserDto> getAvailableBookForUserDto() {
        return bookService.getAllAvailableForUser();
    }

    /**
     * Retrieves all books as a list of {@code BookForUserDto}.
     *
     * @return a list of all books
     */
    public List<BookForUserDto> getAllBookForUserDto() {
        return bookService.getAllForUser();
    }

    /**
     * Retrieves all borrowing records for a specific user based on their email.
     *
     * @param email the email of the user whose borrowing records are to be retrieved
     * @return a list of {@code BorrowingDto} objects representing the user's borrowing records
     */
    public List<BorrowingDto> getAllBorrowingsForUserByEmail(String email) {
        return borrowingService.getAll().stream()
                .filter(e -> e.getUserEmail().equals(email))
                .toList();
    }

    /**
     * Retrieves the name of a user by their email.
     *
     * @param email the email of the user
     * @return the name of the user
     */
    public String getUserNameByEmail(String email) {
        return userService.getUserByEmailForLibrarian(email).getName();
    }

    /**
     * Checks if a user is a librarian by their email.
     *
     * @param email the email of the user
     * @return {@code true} if the user is a librarian, {@code false} otherwise
     */
    public boolean checkIfIsLibrarian(String email) {
        return userService.getUserByEmailForLibrarian(email).getIsLibrarian();
    }

    /**
     * Deletes a book by its ISBN.
     *
     * @param isbn the ISBN of the book to delete
     */
    public void deleteBookByIsbn(String isbn) {
        Integer id = bookService.getBookByIsbnForLibrarian(isbn).getId();
        bookService.deleteBookById(id);
    }

    /**
     * Updates a user in the system.
     *
     * @param dto the {@code UserForLibrarianDto} containing updated user details
     */
    public void updateUser(UserForLibrarianDto dto) {
        UserForLibrarianDto dto2 = userService.getUserByEmailForLibrarian(dto.getEmail());
        dto.setId(dto2.getId());
        userService.updateUser(dto);
    }

    /**
     * Creates a new book in the system.
     *
     * @param dto the {@code BookForLibrarianDto} containing book details
     */
    public void createBook(BookForLibrarianDto dto) {
        bookService.createBook(dto);
    }

    /**
     * Retrieves a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record
     * @return the {@code BorrowingDto} of the specified record
     */
    public BorrowingDto getBorrowingDtoById(Integer id) {
        return borrowingService.getBorrowingById(id);
    }

    /**
     * Updates a borrowing in the system.
     *
     * @param dto the {@code BorrowingDto} containing updated borrowing details
     */
    public void updateBorrowing(BorrowingDto dto) {
        borrowingService.updateBorrowing(dto);
    }

    /**
     * Creates a new borrowing in the system.
     *
     * @param dto the {@code BorrowingDto} containing borrowing details
     */
    public void createBorrowing(BorrowingDto dto) {
        borrowingService.createBorrowing(dto);
    }

    /**
     * Deletes a borrowing by its id.
     *
     * @param id the id of the borrowing to delete
     */
    public void deleteBorrowingById(Integer id) {
        borrowingService.deleteBorrowingById(id);
    }

    /**
     * Retrieves a {@code BookForLibrarianDto} by its ISBN.
     *
     * @param isbn the ISBN of the book to retrieve.
     * @return a {@code BookForLibrarianDto} object containing the book's details for the librarian view.
     */
    public BookForLibrarianDto getBookForLibrarianDtoByIsbn(String isbn) {
        return bookService.getBookByIsbnForLibrarian(isbn);
    }

    /**
     * Updates the details of a book in the system.
     *
     * @param dto the {@code BookForLibrarianDto} containing the updated book details.
     */
    public void updateBook(BookForLibrarianDto dto) {
        BookForLibrarianDto bookDto = bookService.getBookByIsbnForLibrarian(dto.getIsbn());
        dto.setId(bookDto.getId());
        bookService.updateBook(dto);
    }

    /**
     * Retrieves a {@code UserForLibrarianDto} by the user's email address.
     *
     * @param email the email of the user to retrieve.
     * @return a {@code UserForLibrarianDto} object containing the user's details for the librarian view.
     */
    public UserForLibrarianDto getUserForLibrarianDtoByEmail(String email) {
        return userService.getUserByEmailForLibrarian(email);
    }

    /**
     * Retrieves a {@code Librarian} by their email address.
     *
     * @param email the email of the librarian to retrieve.
     * @return a {@code Librarian} object containing the librarian's details.
     */
    public Librarian getLibrarianByEmail(String email) {
        return librarianService.getLibrarianByEmail(email);
    }

    /**
     * Updates the details of a librarian.
     *
     * @param dto      the {@code UserForLibrarianDto} containing the updated user details for the librarian.
     * @param position the updated position of the librarian.
     * @param empDate  the updated employment date of the librarian.
     */
    public void updateLibrarian(UserForLibrarianDto dto, String position, LocalDate empDate) {
        updateUser(dto);
        Librarian librarian = getLibrarianByEmail(dto.getEmail());
        librarian.setPosition(position);
        librarian.setEmploymentDate(empDate);
        librarianService.updateLibrarian(librarian);
    }
}
