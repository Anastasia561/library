package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Copy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

/**
 * The {@code CopyDao} class provides CRUD operations for the {@code Copy} entity.
 * It interacts with the database to manage book copies in the library system.
 */
public class CopyDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code CopyDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public CopyDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code Copy} entity to the database.
     *
     * @param entity the {@code Copy} entity to save
     */
    public void save(Copy entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Retrieves a {@code Copy} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code Copy}
     * @return the {@code Copy} entity with the specified ID, or {@code null} if not found
     */
    public Copy getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Copy.class, id);
    }

    /**
     * Retrieves all {@code Copy} entities from the database.
     *
     * @return a list of all {@code Copy} entities
     */
    public List<Copy> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Copy", Copy.class).list();
    }

    /**
     * Updates the information of an existing {@code Copy} entity in the database.
     *
     * @param copy the {@code Copy} entity with updated information
     */
    public void update(Copy copy) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(copy);
    }

    /**
     * Deletes a {@code Copy} entity from the database.
     *
     * @param copy the {@code Copy} entity to delete
     */
    public void delete(Copy copy) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(copy);
    }

    /**
     * Retrieves a {@code Copy} entity by its copy number.
     *
     * @param copyNumber the copy number of the {@code Copy} to retrieve
     * @return the {@code Copy} entity with the specified copy number, or {@code null} if not found
     */
    public Copy getByCopyNumber(Integer copyNumber) {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Copy> query = session
                .createNativeQuery("select * from copy where copy_number = ?", Copy.class);
        query.setParameter(1, copyNumber);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
