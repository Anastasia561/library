package library.dao;

import library.entity.Book;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDao {
    private final SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Book getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Book.class, id);
    }

    public List<Book> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Book", Book.class).list();
    }

    public Book save(Book entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public Book update(Book entity) {
        return sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(Book entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Integer id) {
        Book entity = getById(id);
        delete(entity);
    }
}
