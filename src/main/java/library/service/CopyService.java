package library.service;

import library.dao.CopyDao;
import library.entity.Copy;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The {@code CopyService} class provides business logic for managing book copies in the library system.
 */
public class CopyService {
    private final SessionFactory sessionFactory;
    private final CopyDao copyDao;
    private Transaction transaction;
    private static CopyService instance;

    /**
     * Creates a new instance of {@code CopyService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private CopyService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        copyDao = new CopyDao(sessionFactory);
    }

    /**
     * Retrieves the singleton instance of {@code CopyService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code CopyService}
     */
    public static CopyService getInstance(String config) {
        if (instance == null) {
            instance = new CopyService(config);
        }
        return instance;
    }

    /**
     * Creates a new book copy.
     *
     * @param copy the {@code Copy} entity representing the book copy
     */
    public void createCopy(Copy copy) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            copyDao.save(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a copy record by its ID.
     *
     * @param id the ID of the copy record
     * @return the {@code Copy} containing the copy details
     * @throws RuntimeException if copy record does not exist in database
     */
    public Copy getCopyById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Copy copy = copyDao.getById(id);
            if (copy == null) {
                throw new RuntimeException("Copy does not exists");
            }
            transaction.commit();
            return copy;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a copy record by its number.
     *
     * @param copyNumber the number of the copy record
     * @return the {@code Copy} containing the copy details
     * @throws RuntimeException if copy record does not exist in database
     */
    public Copy getCopyByCopyNumber(Integer copyNumber) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Copy copy = copyDao.getByCopyNumber(copyNumber);
            if (copy == null) {
                throw new RuntimeException("Copy does not exists");
            }
            transaction.commit();
            return copy;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Updates a copy record.
     *
     * @param copy the {@code Copy} containing copy details
     */
    public void updateCopy(Copy copy) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            copyDao.update(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all book copies.
     *
     * @return a list of {@code Copy} entities
     */
    public List<Copy> getAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Copy> copies = copyDao.findAll();
            transaction.commit();
            return copies;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes a book copy by its ID.
     *
     * @param id the ID of the book copy to delete
     */
    public void deleteCopyById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Copy copy = copyDao.getById(id);
            copyDao.delete(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
