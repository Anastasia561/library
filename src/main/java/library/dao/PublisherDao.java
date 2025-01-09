package library.dao;

import library.entity.Publisher;
import org.hibernate.SessionFactory;

import java.util.List;

public class PublisherDao {
    private final SessionFactory sessionFactory;

    public PublisherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Publisher getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Publisher.class, id);
    }

    public List<Publisher> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Publisher", Publisher.class).list();
    }

    public Publisher save(Publisher entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public Publisher update(Publisher entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(Publisher entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        Publisher entity = getById(id);
        delete(entity);
    }
}
