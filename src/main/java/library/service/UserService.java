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

public class UserService {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;
    private Transaction transaction;
    private static UserService instance;

    private UserService(String conf) {
        sessionFactory = SessionFactoryProvider.getSessionFactory(conf);
        userDao = new UserDao(sessionFactory);
    }

    public static UserService getInstance(String config) {
        if (instance == null) {
            instance = new UserService(config);
        }
        return instance;
    }

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
