package library.service;

import library.dao.BorrowingDao;
import library.dto.BorrowingDto;
import library.entity.Borrowing;
import library.factory.SessionFactoryProvider;
import library.mapper.BorrowingMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowingService {
    private final SessionFactory sessionFactory;
    private final BorrowingDao borrowingDao;
    private Transaction transaction;
    private static BorrowingService instance;

    private final String conf;

    private BorrowingService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        borrowingDao = new BorrowingDao(sessionFactory);
        this.conf = conf;
    }

    public static BorrowingService getInstance(String config) {
        if (instance == null) {
            instance = new BorrowingService(config);
        }
        return instance;
    }

    public void createBorrowing(BorrowingDto dto) {
        if (dto.getReturnDate() != null && dto.getReturnDate().isBefore(dto.getBorrowDate())) {
            throw new RuntimeException("Return date is before borrowing date");
        }
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = BorrowingMapper.toBorrowing(dto);
            borrowingDao.save(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public BorrowingDto getBorrowingById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = borrowingDao.getById(id);
            if (borrowing == null) {
                throw new RuntimeException("Borrowing does not exists");
            }
            transaction.commit();
            return BorrowingMapper.toBorrowingDto(borrowing);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void updateBorrowing(BorrowingDto dto) {
        if (dto.getReturnDate() != null && dto.getReturnDate().isBefore(dto.getBorrowDate())) {
            throw new RuntimeException("Return date is before borrowing date");
        }
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = BorrowingMapper.toBorrowing(dto);
            borrowingDao.update(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<BorrowingDto> getAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<Borrowing> borrowings = borrowingDao.findAll();
            transaction.commit();
            return borrowings.stream().map(BorrowingMapper::toBorrowingDto).toList();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteBorrowingById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            Borrowing borrowing = borrowingDao.getById(id);
            borrowingDao.delete(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
