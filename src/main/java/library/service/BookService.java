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

public class BookService {
    private final SessionFactory sessionFactory;
    private final BookDao bookDao;
    private Transaction transaction;
    private static BookService instance;

    private BookService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        bookDao = new BookDao(sessionFactory);
    }

    public static BookService getInstance(String config) {
        if (instance == null) {
            instance = new BookService(config);
        }
        return instance;
    }

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
