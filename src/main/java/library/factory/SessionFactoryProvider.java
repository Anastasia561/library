package library.factory;

import library.entity.Book;
import library.entity.Borrowing;
import library.entity.Copy;
import library.entity.Librarian;
import library.entity.Publisher;
import library.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
    public static SessionFactory getSessionFactory() {
        return new Configuration()
                .configure()
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Borrowing.class)
                .addAnnotatedClass(Copy.class)
                .addAnnotatedClass(Librarian.class)
                .addAnnotatedClass(Publisher.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}
