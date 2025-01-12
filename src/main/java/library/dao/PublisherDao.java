package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PublisherDao {
    private final SessionFactory sessionFactory;

    public PublisherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Publisher entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public Publisher getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Publisher.class, id);
    }

    public List<Publisher> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Publisher", Publisher.class).list();
    }

    public void update(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(publisher);
    }

    public void delete(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(publisher);
    }

    public Publisher getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Publisher> query = session
                .createNativeQuery("select * from publisher where name = ?", Publisher.class);
        query.setParameter(1, name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
