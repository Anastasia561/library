package library.dao;

import library.entity.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PublisherDao {
    private final SessionFactory sessionFactory;

    public PublisherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(PublisherDao entity) {
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
}
