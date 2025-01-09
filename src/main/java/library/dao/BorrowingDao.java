package library.dao;

import library.entity.Borrowing;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BorrowingDao {
    private final SessionFactory sessionFactory;

    public BorrowingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Borrowing entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public Borrowing getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Borrowing.class, id);
    }

    public List<Borrowing> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Borrowing", Borrowing.class).list();
    }

    public void update(Borrowing borrowing) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(borrowing);
    }

    public void delete(Borrowing borrowing) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(borrowing);
    }
}
