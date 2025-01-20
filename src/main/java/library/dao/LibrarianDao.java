package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Librarian;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

/**
 * The {@code LibrarianDao} class provides CRUD operations for the {@code Librarian} entity.
 * It interacts with the database to manage librarian records in the library system.
 */
public class LibrarianDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code LibrarianDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public LibrarianDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code Librarian} entity to the database.
     *
     * @param entity the {@code Librarian} entity to save
     */
    public void save(Librarian entity) {
        NativeQuery<Librarian> query = sessionFactory.getCurrentSession()
                .createNativeQuery("insert into librarian " +
                        "(employment_date, position, user_id) " +
                        "values (?, ?, ?)", Librarian.class);
        query.setParameter(1, entity.getEmploymentDate());
        query.setParameter(2, entity.getPosition());
        query.setParameter(3, entity.getUser().getId());
        query.executeUpdate();
    }

    /**
     * Retrieves a {@code Librarian} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code Librarian}
     * @return the {@code Librarian} entity with the specified ID, or {@code null} if not found
     */
    public Librarian getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Librarian.class, id);
    }

    /**
     * Retrieves all {@code Librarian} entities from the database.
     *
     * @return a list of all {@code Librarian} entities
     */
    public List<Librarian> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Librarian", Librarian.class).list();
    }

    /**
     * Updates the information of an existing {@code Librarian} entity in the database.
     *
     * @param librarian the {@code Librarian} entity with updated information
     */
    public void update(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(librarian);
    }

    /**
     * Deletes a {@code Librarian} entity from the database.
     *
     * @param librarian the {@code Librarian} entity to delete
     */
    public void delete(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(librarian);
    }

    /**
     * Retrieves a {@code Librarian} entity by its associated email.
     *
     * @param email the email of the {@code Librarian} to retrieve
     * @return the {@code Librarian} entity with the specified email, or {@code null} if not found
     */
    public Librarian getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Librarian> query = session
                .createQuery("from Librarian l where l.user.email=:email", Librarian.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
