package library.dao;

import jakarta.persistence.NoResultException;
import library.entity.Librarian;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class LibrarianDao {
    private final SessionFactory sessionFactory;

    public LibrarianDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

    public Librarian getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Librarian.class, id);
    }

    public List<Librarian> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Librarian", Librarian.class).list();
    }

    public void update(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(librarian);
    }

    public void delete(Librarian librarian) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(librarian);
    }

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
