package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Copy;
import library.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

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

    public Copy getByCopyNumber(Integer copyNumber) {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Copy> query = session
                .createNativeQuery("select * from copy where copy_number = ?", Copy.class);
        query.setParameter(1, copyNumber);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
