package library.dao;

import org.hibernate.SessionFactory;

import java.util.List;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDao getById(Integer id) {
        return sessionFactory.getCurrentSession().get(UserDao.class, id);
    }

    public List<UserDao> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from User", UserDao.class).list();
    }

    public UserDao save(UserDao entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public UserDao update(UserDao entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(UserDao entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        UserDao entity = getById(id);
        delete(entity);
    }
}
