package library.dao;

import library.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(User entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public User getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User", User.class).list();
    }

    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(user);
    }
}
