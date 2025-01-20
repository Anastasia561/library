package library.service;

import library.dao.BookDao;
import library.dto.BookForLibrarianDto;
import library.dto.BookForUserDto;
import library.entity.Book;
import library.factory.SessionFactoryProvider;
import library.mapper.BookMapper;
import library.validator.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The {@code BookService} class provides business logic for managing books in the library system.
 * It handles operations such as creating, retrieving, updating, and deleting books.
 */
public class BookService {
    private final SessionFactory sessionFactory;
    private final BookDao bookDao;
    private Transaction transaction;
    private static BookService instance;

    /**
     * Creates a new instance of {@code BookService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private BookService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        bookDao = new BookDao(sessionFactory);
    }

    /**
     * Retrieves the singleton instance of {@code BookService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code BookService}
     */
    public static BookService getInstance(String config) {
        if (instance == null) {
            instance = new BookService(config);
        }
        return instance;
    }

    /**
     * Creates a new book in the system.
     *
     * @param dto the {@code BookForLibrarianDto} containing book details
     */
    public void createBook(BookForLibrarianDto dto) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();

            Book book = BookMapper.toBookFromForLibrarianDto(dto);
            Validator.validate(book);
            bookDao.save(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a book by its ISBN for librarian views.
     *
     * @param isbn the ISBN of the book
     * @return the {@code BookForLibrarianDto} containing book details
     */
    public BookForLibrarianDto getBookByIsbnForLibrarian(String isbn) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Book book = bookDao.getByIsbn(isbn);
            if (book == null) {
                throw new RuntimeException("Book does not exists");
            }
            transaction.commit();
            return BookMapper.toForLibrarianDto(book);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a book by its id for librarian views.
     *
     * @param id the id of the book
     * @return the {@code BookForLibrarianDto} containing book details
     * @throws RuntimeException if there is no book with this ISBN in database
     */
    public BookForLibrarianDto getBookByIdForLibrarian(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Book book = bookDao.getById(id);
            if (book == null) {
                throw new RuntimeException("Book does not exists");
            }
            transaction.commit();
            return BookMapper.toForLibrarianDto(book);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a book by its id for librarian views.
     *
     * @param id the id of the book
     * @return the {@code BookForLibrarianDto} containing book details
     * @throws RuntimeException if there is no book with such id in database
     */
    public BookForUserDto getBookByIdForUser(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Book book = bookDao.getById(id);
            if (book == null) {
                throw new RuntimeException("Book does not exists");
            }
            transaction.commit();
            return BookMapper.toForUserDto(book);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Updates the information of a book in the database based on the provided DTO.
     *
     * @param dto the {@code BookForLibrarianDto} containing updated book information.
     * @throws RuntimeException if an error occurs during the update operation, such as validation failure or database constraints.
     */
    public void updateBook(BookForLibrarianDto dto) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();

            Book book = BookMapper.toBookFromForLibrarianDto(dto);
            Validator.validate(book);
            bookDao.update(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all books for librarian view.
     *
     * @return a list of {@code BookForLibrarianDto} objects representing all books
     */
    public List<BookForLibrarianDto> getAllForLibrarian() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Book> books = bookDao.findAll();
            transaction.commit();
            return books.stream().map(BookMapper::toForLibrarianDto).toList();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all books for user view.
     *
     * @return a list of {@code BookForLibrarianDto} objects representing all books
     */
    public List<BookForUserDto> getAllForUser() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Book> books = bookDao.findAll();
            transaction.commit();
            return books.stream().map(BookMapper::toForUserDto).toList();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all currently available books for user view.
     *
     * @return a list of {@code BookForLibrarianDto} objects representing all books
     */
    public List<BookForUserDto> getAllAvailableForUser() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Book> books = bookDao.findAll();
            List<Book> available = books.stream().filter(e ->
                    e.getCopies().stream().anyMatch(a -> a.getStatus().equals("Available"))).toList();
            transaction.commit();
            return available.stream().map(BookMapper::toForUserDto).toList();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     */
    public void deleteBookById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Book book = bookDao.getById(id);
            bookDao.delete(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
