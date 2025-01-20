package library.service;

import library.dao.BorrowingDao;
import library.dto.BorrowingDto;
import library.entity.Borrowing;
import library.entity.Copy;
import library.factory.SessionFactoryProvider;
import library.mapper.BorrowingMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The {@code BorrowingService} class provides business logic for managing borrowings in the library system.
 * It handles operations such as creating, retrieving, updating, and deleting borrowing records.
 */
public class BorrowingService {
    private final SessionFactory sessionFactory;
    private final BorrowingDao borrowingDao;
    private Transaction transaction;
    private static BorrowingService instance;
    private final String conf;

    /**
     * Creates a new instance of {@code BorrowingService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private BorrowingService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        borrowingDao = new BorrowingDao(sessionFactory);
        this.conf = conf;
    }

    /**
     * Retrieves the singleton instance of {@code BorrowingService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code BorrowingService}
     */
    public static BorrowingService getInstance(String config) {
        if (instance == null) {
            instance = new BorrowingService(config);
        }
        return instance;
    }

    /**
     * Creates a new borrowing record.
     *
     * @param dto the {@code BorrowingDto} containing borrowing details
     * @throws RuntimeException if the return date is before the borrowing date
     */
    public void createBorrowing(BorrowingDto dto) {
        if (dto.getReturnDate() != null && dto.getReturnDate().isBefore(dto.getBorrowDate())) {
            throw new RuntimeException("Return date is before borrowing date");
        }
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            CopyService copyService = CopyService.getInstance(conf);
            Copy copy = copyService.getCopyByCopyNumber(dto.getCopyNumber());
            copy.setStatus("Borrowed");
            copyService.updateCopy(copy);
            Borrowing borrowing = BorrowingMapper.toBorrowing(dto);
            borrowingDao.save(borrowing);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record
     * @return the {@code BorrowingDto} containing the borrowing details
     * @throws RuntimeException if borrowing record does not exist in database
     */
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

    /**
     * Updates a borrowing record.
     *
     * @param dto the {@code BorrowingDto} containing borrowing details
     * @throws RuntimeException if the return date is before the borrowing date
     */
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

    /**
     * Retrieves all borrowing records.
     *
     * @return a list of {@code BorrowingDto} objects representing all borrowings
     */
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

    /**
     * Deletes a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record to delete
     */
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
