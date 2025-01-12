package library.service;

import library.dao.PublisherDao;
import library.entity.Publisher;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PublisherService {
    private final SessionFactory sessionFactory;
    private final PublisherDao publisherDao;
    Transaction transaction;

    public PublisherService() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
        publisherDao = new PublisherDao(sessionFactory);
    }

    public void createPublisher(Publisher publisher) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            publisherDao.save(publisher);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Publisher getPublisherById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Publisher publisher = publisherDao.getById(id);
            if (publisher == null) {
                throw new RuntimeException("Publisher does not exists");
            }
            transaction.commit();
            return publisher;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Publisher getPublisherByName(String name) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Publisher publisher = publisherDao.getByName(name);
            if (publisher == null) {
                throw new RuntimeException("Publisher does not exists");
            }
            transaction.commit();
            return publisher;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updatePublisher(Publisher publisher) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            publisherDao.update(publisher);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Publisher> getAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Publisher> publishers = publisherDao.findAll();
            transaction.commit();
            return publishers;
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deletePublisherById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Publisher publisher = publisherDao.getById(id);
            publisherDao.delete(publisher);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
