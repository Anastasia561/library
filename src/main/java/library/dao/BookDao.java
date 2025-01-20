package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

/**
 * The {@code BookDao} class provides CRUD operations for the {@code Book} entity using Hibernate.
 * It interacts with the database to perform operations such as saving, retrieving, updating, and deleting books.
 */
public class BookDao {

    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code BookDao} with the specified Hibernate {@code SessionFactory}.
     *
     * @param sessionFactory the {@code SessionFactory} used to interact with the database
     */
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves a {@code Book} entity to the database.
     *
     * @param entity the {@code Book} entity to save
     */
    public void save(Book entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * Retrieves a {@code Book} entity by its unique ID.
     *
     * @param id the unique identifier of the {@code Book}
     * @return the {@code Book} entity with the specified ID, or {@code null} if not found
     */
    public Book getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    /**
     * Retrieves all {@code Book} entities from the database.
     *
     * @return a list of all {@code Book} entities
     */
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).list();
    }

    /**
     * Updates the information of an existing {@code Book} entity in the database.
     *
     * @param book the {@code Book} entity with updated information
     */
    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    /**
     * Deletes a {@code Book} entity from the database.
     *
     * @param book the {@code Book} entity to delete
     */
    public void delete(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(book);
    }

    /**
     * Retrieves a {@code Book} entity by its ISBN.
     *
     * @param isbn the ISBN of the {@code Book} to retrieve
     * @return the {@code Book} entity with the specified ISBN, or {@code null} if not found
     */
    public Book getByIsbn(String isbn) {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Book> query = session
                .createNativeQuery("select * from book where isbn = ?", Book.class);
        query.setParameter(1, isbn);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
