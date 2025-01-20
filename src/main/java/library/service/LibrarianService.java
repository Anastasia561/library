package library.service;

import library.dao.LibrarianDao;
import library.entity.Librarian;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * The {@code LibrarianService} class provides business logic for managing librarians in the library system.
 */
public class LibrarianService {
    private final SessionFactory sessionFactory;
    private final LibrarianDao librarianDao;
    private Transaction transaction;
    private static LibrarianService instance;

    /**
     * Creates a new instance of {@code LibrarianService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private LibrarianService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        librarianDao = new LibrarianDao(sessionFactory);
    }

    /**
     * Retrieves the singleton instance of {@code LibrarianService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code LibrarianService}
     */
    public static LibrarianService getInstance(String config) {
        if (instance == null) {
            instance = new LibrarianService(config);
        }
        return instance;
    }

    /**
     * Creates a new librarian record.
     *
     * @param librarian the {@code Librarian} entity representing the librarian
     * @throws RuntimeException if the employment date is in the future
     */
    public void createLibrarian(Librarian librarian) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            if (librarian.getEmploymentDate().isAfter(LocalDate.now())) {
                throw new RuntimeException("Employment date is in the future");
            } else {
                librarianDao.save(librarian);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a librarian record by its ID.
     *
     * @param id the ID of the librarian record
     * @return the {@code Librarian} containing the librarian details
     * @throws RuntimeException if librarian record does not exist in database
     */
    public Librarian getLibrarianById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Librarian librarian = librarianDao.getById(id);
            if (librarian == null) {
                throw new RuntimeException("Librarian does not exists");
            }
            transaction.commit();
            return librarian;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a librarian record by its email.
     *
     * @param email the ID of the librarian record
     * @return the {@code Librarian} containing the librarian details
     * @throws RuntimeException if librarian record does not exist in database
     */
    public Librarian getLibrarianByEmail(String email) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Librarian librarian = librarianDao.getByEmail(email);
            if (librarian == null) {
                throw new RuntimeException("Librarian does not exists");
            }
            transaction.commit();
            return librarian;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Updates a librarian record.
     *
     * @param librarian the {@code Librarian} containing librarian details
     * @throws RuntimeException if employment date of the librarian is in the future
     */
    public void updateLibrarian(Librarian librarian) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            if (librarian.getEmploymentDate().isAfter(LocalDate.now())) {
                throw new RuntimeException("Employment date is in the future");
            } else {
                librarianDao.update(librarian);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all librarians.
     *
     * @return a list of {@code Librarian} entities
     */
    public List<Librarian> getAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Librarian> librarians = librarianDao.findAll();
            transaction.commit();
            return librarians;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes a librarian by their ID.
     *
     * @param id the ID of the librarian to delete
     */
    public void deleteLibrarianById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Librarian librarian = librarianDao.getById(id);
            librarianDao.delete(librarian);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
