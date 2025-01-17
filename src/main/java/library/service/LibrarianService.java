package library.service;

import library.dao.LibrarianDao;
import library.entity.Librarian;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class LibrarianService {
    private final SessionFactory sessionFactory;
    private final LibrarianDao librarianDao;
    private Transaction transaction;

    public LibrarianService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        librarianDao = new LibrarianDao(sessionFactory);
    }

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
