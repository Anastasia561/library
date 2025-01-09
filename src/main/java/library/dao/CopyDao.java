package library.dao;

import library.entity.Copy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CopyDao {
    private final SessionFactory sessionFactory;

    public CopyDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Copy entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public Copy getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Copy.class, id);
    }

    public List<Copy> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Copy", Copy.class).list();
    }

    public void update(Copy copy) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(copy);
    }

    public void delete(Copy copy) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(copy);
    }
}
