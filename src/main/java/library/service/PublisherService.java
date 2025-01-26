package library.service;

import library.dao.PublisherDao;
import library.entity.Publisher;
import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The {@code PublisherService} class provides business logic for managing publishers in the library system.
 */
public class PublisherService {
    private final SessionFactory sessionFactory;
    private final PublisherDao publisherDao;
    Transaction transaction;
    private static PublisherService instance;

    /**
     * Creates a new instance of {@code PublisherService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private PublisherService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        publisherDao = new PublisherDao(sessionFactory);
    }

    /**
     * Retrieves the singleton instance of {@code PublisherService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code PublisherService}
     */
    public static PublisherService getInstance(String config) {
        if (instance == null) {
            instance = new PublisherService(config);
        }
        return instance;
    }

    /**
     * Creates a new publisher record.
     *
     * @param publisher the {@code Publisher} entity representing the publisher
     */
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

    /**
     * Retrieves a publisher record by its ID.
     *
     * @param id the ID of the publisher record
     * @return the {@code Publisher} containing the publisher details
     * @throws RuntimeException if publisher record does not exist in database
     */
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

    /**
     * Retrieves a publisher record by its name.
     *
     * @param name the ID of the publisher record
     * @return the {@code Publisher} containing the publisher details
     * @throws RuntimeException if publisher record does not exist in database
     */
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

    /**
     * Updates a publisher record.
     *
     * @param publisher the {@code Publisher} containing publisher details
     */
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

    /**
     * Retrieves all publishers.
     *
     * @return a list of {@code Publisher} entities
     */
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

    /**
     * Deletes a publisher by their ID.
     *
     * @param id the ID of the publisher to delete
     */
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
