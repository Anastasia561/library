package library.service;

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

public class AbstractBaseServiceTest {
    protected SessionFactory sessionFactory;
    protected Session session;

    @BeforeEach
    void setup() {
        sessionFactory = SessionFactoryProvider.getSessionFactory("h2.cfg.xml");

        runSqlScriptFile("src/test/resources/schema.sql");
        runSqlScriptFile("src/test/resources/data.sql");
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterEach
    void tear() {
        runSqlScriptFile("src/test/resources/drop.sql");
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
