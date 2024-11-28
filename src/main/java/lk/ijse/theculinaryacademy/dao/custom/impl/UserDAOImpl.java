package lk.ijse.theculinaryacademy.dao.custom.impl;

import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dao.custom.UserDAO;
import lk.ijse.theculinaryacademy.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void add(User entity) {
        Session userSaveSession = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = userSaveSession.beginTransaction();
        userSaveSession.save(entity);
        transaction.commit();
        userSaveSession.close();
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }
}
