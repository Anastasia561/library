package library.service;

import library.dao.CopyDao;
import library.entity.Copy;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CopyService {
    private final SessionFactory sessionFactory;
    private final CopyDao copyDao;
    private Transaction transaction;

    public CopyService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        copyDao = new CopyDao(sessionFactory);
    }

    public void createCopy(Copy copy) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            copyDao.save(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Copy getCopyById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            Copy copy = copyDao.getById(id);
            if (copy == null) {
                throw new RuntimeException("Copy does not exists");
            }
            transaction.commit();
            return copy;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updateCopy(Copy copy) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            copyDao.update(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Copy> getAll() {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            List<Copy> copies = copyDao.findAll();
            transaction.commit();
            return copies;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteCopyById(Integer id) {
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            Copy copy = copyDao.getById(id);
            copyDao.delete(copy);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
