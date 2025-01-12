package library.service;

import library.dao.UserDao;
import library.entity.User;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserService {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;
    private Transaction transaction;

    public UserService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        userDao = new UserDao(sessionFactory);
    }

    public void createUser(User user) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            userDao.save(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public User getUserById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            User user = userDao.getById(id);
            if (user == null) {
                throw new RuntimeException("User does not exists");
            }
            transaction.commit();
            return user;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updateUser(User user) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            userDao.update(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<User> getAll() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            List<User> users = userDao.findAll();
            transaction.commit();
            return users;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteUserById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            User user = userDao.getById(id);
            userDao.delete(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
