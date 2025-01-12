package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Librarian;
import library.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class LibrarianDao {
    private final SessionFactory sessionFactory;

    public LibrarianDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Librarian entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public Librarian getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Librarian.class, id);
    }

    public List<Librarian> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Librarian", Librarian.class).list();
    }

    public void update(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(librarian);
    }

    public void delete(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(librarian);
    }

    public Librarian getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Librarian> query = session
                .createNativeQuery("select * from librarian where email = ?", Librarian.class);
        query.setParameter(1, email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
