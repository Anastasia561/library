package library.factory;

import library.entity.Book;
import library.entity.Borrowing;
import library.entity.Copy;
import library.entity.Librarian;
import library.entity.Publisher;
import library.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The {@code SessionFactoryProvider} class provides a method to configure and obtain a Hibernate {@code SessionFactory}.
 * The {@code SessionFactory} is configured based on the specified configuration file and annotated entity classes.
 */
public class SessionFactoryProvider {

    /**
     * Creates and returns a Hibernate {@code SessionFactory} configured with the specified configuration file
     * and annotated entity classes.
     *
     * @param cfgPath the path to the Hibernate configuration file
     * @return a configured {@code SessionFactory} instance
     * @throws org.hibernate.HibernateException if there is an error during configuration or building of the {@code SessionFactory}
     */
    public static SessionFactory getSessionFactory(String cfgPath) {
        return new Configuration()
                .configure(cfgPath)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Borrowing.class)
                .addAnnotatedClass(Copy.class)
                .addAnnotatedClass(Librarian.class)
                .addAnnotatedClass(Publisher.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}
