package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * The {@code UserDao} class provides CRUD operations for the {@code User} entity.
 * It interacts with the database to manage user records in the library system.
 */
public class UserDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code UserDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code User} entity to the database.
     *
     * @param entity the {@code User} entity to save
     */
    public void save(User entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Retrieves a {@code User} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code User}
     * @return the {@code User} entity with the specified ID, or {@code null} if not found
     */
    public User getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    /**
     * Retrieves all {@code User} entities from the database.
     *
     * @return a list of all {@code User} entities
     */
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User", User.class).list();
    }

    /**
     * Updates the information of an existing {@code User} entity in the database.
     *
     * @param user the {@code User} entity with updated information
     */
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    /**
     * Deletes a {@code User} entity from the database.
     *
     * @param user the {@code User} entity to delete
     */
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(user);
    }

    /**
     * Retrieves a {@code User} entity by its email.
     *
     * @param email the email of the {@code User} to retrieve
     * @return the {@code User} entity with the specified email, or {@code null} if not found
     */
    public User getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session
                .createQuery("FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
