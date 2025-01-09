package library.dao;

import library.entity.Librarian;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
