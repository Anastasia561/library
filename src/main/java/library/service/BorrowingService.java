package library.service;

import library.dao.BorrowingDao;
import library.entity.Borrowing;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowingService {
    private final SessionFactory sessionFactory;
    private final BorrowingDao borrowingDao;
    private Transaction transaction;

    public BorrowingService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        borrowingDao = new BorrowingDao(sessionFactory);
    }

    public void createBorrowing(Borrowing borrowing) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            borrowingDao.save(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Borrowing getBorrowingById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = borrowingDao.getById(id);
            if (borrowing == null) {
                throw new RuntimeException("Borrowing does not exists");
            }
            transaction.commit();
            return borrowing;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updateBorrowing(Borrowing borrowing) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            borrowingDao.update(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Borrowing> getAll() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            List<Borrowing> borrowings = borrowingDao.findAll();
            transaction.commit();
            return borrowings;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteBorrowingById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = borrowingDao.getById(id);
            borrowingDao.delete(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
