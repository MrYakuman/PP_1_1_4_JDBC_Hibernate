package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDaoJDBCImpl();

            userDao.createUsersTable();

            userDao.saveUser("Steve", "Vai", (byte) 62);
            userDao.saveUser("Joseph", "Satriani", (byte) 66);
            userDao.saveUser("Paul", "Gilbert", (byte) 56);
            userDao.saveUser("Michael", "Batio", (byte) 66);

            List<User> userArrayList = userDao.getAllUsers();
            for (User user : userArrayList) {
                System.out.println(user.toString());
            }

            userDao.cleanUsersTable();

            userDao.dropUsersTable();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
