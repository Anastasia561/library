package library;

import library.factory.SessionFactoryProvider;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
    }
}
