package library.service;

import library.dao.UserDao;
import library.dto.UserForLibrarianDto;
import library.dto.UserInfoDto;
import library.entity.User;
import library.factory.SessionFactoryProvider;
import library.mapper.UserMapper;
import library.validator.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The {@code UserService} class provides business logic for managing users in the library system.
 */
public class UserService {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;
    private Transaction transaction;
    private static UserService instance;

    /**
     * Creates a new instance of {@code UserService} with the specified configuration file.
     *
     * @param conf the path to the Hibernate configuration file
     */
    private UserService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        userDao = new UserDao(sessionFactory);
    }

    /**
     * Retrieves the singleton instance of {@code UserService}.
     *
     * @param config the path to the Hibernate configuration file
     * @return the singleton instance of {@code UserService}
     */
    public static UserService getInstance(String config) {
        if (instance == null) {
            instance = new UserService(config);
        }
        return instance;
    }

    /**
     * Creates a new user record.
     *
     * @param dto the {@code UserForLibrarianDto} containing user details
     * @throws RuntimeException if the email is not unique
     */
    public void createUser(UserForLibrarianDto dto) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();

            User user = UserMapper.toUserFromUserForLibrarianDto(dto, true);
            Validator.validate(user);
            if (userDao.getByEmail(dto.getEmail()) == null) {
                userDao.save(user);
            } else {
                throw new RuntimeException("Email is not unique");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a user record by its ID for librarian view.
     *
     * @param id the ID of the user record
     * @return the {@code UserForLibrarianDto} containing the user details
     * @throws RuntimeException if user record does not exist in database
     */
    public UserForLibrarianDto getUserByIdForLibrarian(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            User user = userDao.getById(id);
            if (user == null) {
                throw new RuntimeException("User does not exist");
            }
            transaction.commit();
            return UserMapper.toUserForLibrarianDto(user);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a user record by its ID for user view.
     *
     * @param id the ID of the user record
     * @return the {@code UserInfoDto} containing the user details
     * @throws RuntimeException if user record does not exist in database
     */
    public UserInfoDto getUserByIdInfo(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            User user = userDao.getById(id);
            if (user == null) {
                throw new RuntimeException("User does not exist");
            }
            transaction.commit();
            return UserMapper.toUserInfoDto(user);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves a user record by its ID for librarian view.
     *
     * @param email the ID of the user record
     * @return the {@code UserForLibrarianDto} containing the user details
     * @throws RuntimeException if user record does not exist in database
     */
    public UserForLibrarianDto getUserByEmailForLibrarian(String email) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            User user = userDao.getByEmail(email);
            if (user == null) {
                throw new RuntimeException("User does not exist");
            }
            transaction.commit();
            return UserMapper.toUserForLibrarianDto(user);
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Updates a user record.
     *
     * @param dto the {@code UserForLibrarianDto} containing user details
     */
    public void updateUser(UserForLibrarianDto dto) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();

            User user = UserMapper.toUserFromUserForLibrarianDto(dto, true);
            Validator.validate(user);
            userDao.update(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Retrieves all users for librarians.
     *
     * @return a list of {@code UserForLibrarianDto} objects representing all users
     */
    public List<UserForLibrarianDto> getAllForLibrarian() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            List<User> users = userDao.findAll();
            transaction.commit();
            return users.stream().map(UserMapper::toUserForLibrarianDto).toList();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUserById(Integer id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            User user = userDao.getById(id);
            userDao.delete(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
