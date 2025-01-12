package library.dao;

import library.entity.Book;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDao {
    private final SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Book entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public Book getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
//        return session.get(Book.class, id);
        Book book = session.get(Book.class, id);
        if (book != null) {
            Hibernate.initialize(book.getCopies());
        }
        return book;
    }

    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).list();
    }

    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    public void delete(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(book);
    }
}
