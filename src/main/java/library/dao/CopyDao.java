package library.dao;

import library.entity.Copy;
import org.hibernate.SessionFactory;

import java.util.List;

public class CopyDao {
    private final SessionFactory sessionFactory;

    public CopyDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Copy getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Copy.class, id);
    }

    public List<Copy> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Copy", Copy.class).list();
    }

    public Copy save(Copy entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public Copy update(Copy entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(Copy entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        Copy entity = getById(id);
        delete(entity);
    }
}
