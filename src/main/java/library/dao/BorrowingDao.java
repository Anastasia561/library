package library.dao;

import library.entity.Borrowing;
import org.hibernate.SessionFactory;

import java.util.List;

public class BorrowingDao {
    private final SessionFactory sessionFactory;

    public BorrowingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Borrowing getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Borrowing.class, id);
    }

    public List<Borrowing> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Borrowing", Borrowing.class).list();
    }

    public Borrowing save(Borrowing entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public Borrowing update(Borrowing entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(Borrowing entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        Borrowing entity = getById(id);
        delete(entity);
    }
}
