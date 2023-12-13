package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory connectionHiber = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = connectionHiber.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "UserName VARCHAR(50) NOT NULL, LastName VARCHAR(50) NOT NULL, " +
                            "AGE TINYINT NOT NULL)")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = connectionHiber.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery("DROP TABLE  IF EXISTS users")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = connectionHiber.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }


    }

    @Override
    public void removeUserById(long id) {
        try(Session session = connectionHiber.getCurrentSession()) {
            session.beginTransaction();
            User uska = session.get(User.class,id);
            session.delete(uska);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> uska = new ArrayList<>();
        try (Session session = connectionHiber.getCurrentSession()) {
            session.beginTransaction();
            uska = session.createQuery("from User ", User.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return uska;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = connectionHiber.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users ")
                    .executeUpdate();
            session.getTransaction().commit();
        }

    }
}
