package jm.task.core.jdbc;

import jdk.jshell.execution.Util;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import javax.crypto.spec.PSource;

public class Main {
    public static void main(String[] args) {
         UserService userDao = new UserServiceImpl();
//        userDao.createUsersTable();

//        userDao.saveUser("Gena", "AllIn", (byte) 20);
//        userDao.saveUser("Name2", "LastName2", (byte) 25);
//        userDao.saveUser("Name3", "LastName3", (byte) 31);
//        userDao.saveUser("Name4", "LastName4", (byte) 38);

//        userDao.removeUserById(5);
//        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();
//        userDao.dropUsersTable();

    }
}
