package library.service;

import library.dao.BookDao;
import library.entity.Book;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookService {
    private final SessionFactory sessionFactory;
    private final BookDao bookDao;
    private Transaction transaction;

    public BookService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        bookDao = new BookDao(sessionFactory);
    }

    public void createBook(Book book) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            bookDao.save(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Book getBookById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            Book book = bookDao.getById(id);
            if (book == null) {
                throw new RuntimeException("Book does not exists");
            }
            transaction.commit();
            return book;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updateBook(Book book) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            bookDao.update(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Book> getAll() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            List<Book> books = bookDao.findAll();
            transaction.commit();
            return books;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteBookById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
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
