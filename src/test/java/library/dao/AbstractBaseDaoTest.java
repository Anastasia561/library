package library.dao;

import library.factory.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractBaseDaoTest {
    protected SessionFactory sessionFactory;
    protected Session session;
    protected BookDao bookDao;
    protected BorrowingDao borrowingDao;
    protected CopyDao copyDao;
    protected LibrarianDao librarianDao;
    protected PublisherDao publisherDao;
    protected UserDao userDao;

    @BeforeEach
    void setup() {
        sessionFactory = SessionFactoryProvider.getSessionFactory("h2.cfg.xml");

        bookDao = new BookDao(sessionFactory);
        borrowingDao = new BorrowingDao(sessionFactory);
        copyDao = new CopyDao(sessionFactory);
        librarianDao = new LibrarianDao(sessionFactory);
        publisherDao = new PublisherDao(sessionFactory);
        userDao = new UserDao(sessionFactory);

        runSqlScriptFile("src/test/resources/schema.sql");
        runSqlScriptFile("src/test/resources/data.sql");
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterEach
    void tear() {
        session.getTransaction().commit();
        sessionFactory.close();
    }

    void runSqlScriptFile(String path) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sqlScript = new String(Files.readAllBytes(Paths.get(path)));
            Query query = session.createNativeQuery(sqlScript);
            query.executeUpdate();

            transaction.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
