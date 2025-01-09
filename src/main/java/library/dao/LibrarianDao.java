package library.dao;

import library.entity.Librarian;
import org.hibernate.SessionFactory;

import java.util.List;

public class LibrarianDao {
    private final SessionFactory sessionFactory;

    public LibrarianDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Librarian getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Librarian.class, id);
    }

    public List<Librarian> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Librarian", Librarian.class).list();
    }

    public Librarian save(Librarian entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public Librarian update(Librarian entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(Librarian entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        Librarian entity = getById(id);
        delete(entity);
    }
}
