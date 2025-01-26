package library.dao;

import library.entity.Borrowing;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * The {@code BorrowingDao} class provides CRUD operations for the {@code Borrowing} entity.
 * It interacts with the database to manage borrowing records in the library system.
 */
public class BorrowingDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code BorrowingDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public BorrowingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code Borrowing} entity to the database.
     *
     * @param entity the {@code Borrowing} entity to save
     */
    public void save(Borrowing entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Retrieves a {@code Borrowing} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code Borrowing}
     * @return the {@code Borrowing} entity with the specified ID, or {@code null} if not found
     */
    public Borrowing getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Borrowing.class, id);
    }

    /**
     * Retrieves all {@code Borrowing} entities from the database.
     *
     * @return a list of all {@code Borrowing} entities
     */
    public List<Borrowing> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Borrowing", Borrowing.class).list();
    }

    /**
     * Updates the information of an existing {@code Borrowing} entity in the database.
     *
     * @param borrowing the {@code Borrowing} entity with updated information
     */
    public void update(Borrowing borrowing) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(borrowing);
    }

    /**
     * Deletes a {@code Borrowing} entity from the database.
     *
     * @param borrowing the {@code Borrowing} entity to delete
     */
    public void delete(Borrowing borrowing) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(borrowing);
    }
}
