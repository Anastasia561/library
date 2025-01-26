package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

/**
 * The {@code PublisherDao} class provides CRUD operations for the {@code Publisher} entity.
 * It interacts with the database to manage publisher records in the library system.
 */
public class PublisherDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code PublisherDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public PublisherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code Publisher} entity to the database.
     *
     * @param entity the {@code Publisher} entity to save
     */
    public void save(Publisher entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Retrieves a {@code Publisher} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code Publisher}
     * @return the {@code Publisher} entity with the specified ID, or {@code null} if not found
     */
    public Publisher getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Publisher.class, id);
    }

    /**
     * Retrieves all {@code Publisher} entities from the database.
     *
     * @return a list of all {@code Publisher} entities
     */
    public List<Publisher> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Publisher", Publisher.class).list();
    }

    /**
     * Updates the information of an existing {@code Publisher} entity in the database.
     *
     * @param publisher the {@code Publisher} entity with updated information
     */
    public void update(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(publisher);
    }

    /**
     * Deletes a {@code Publisher} entity from the database.
     *
     * @param publisher the {@code Publisher} entity to delete
     */
    public void delete(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(publisher);
    }

    /**
     * Retrieves a {@code Publisher} entity by its name.
     *
     * @param name the name of the {@code Publisher} to retrieve
     * @return the {@code Publisher} entity with the specified name, or {@code null} if not found
     */
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
