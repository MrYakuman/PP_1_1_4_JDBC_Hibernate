package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

            userDaoJDBC.createUsersTable();

            userDaoJDBC.saveUser("Steve", "Vai", (byte) 62);
            userDaoJDBC.saveUser("Joseph", "Satriani", (byte) 66);
            userDaoJDBC.saveUser("Paul", "Gilbert", (byte) 56);
            userDaoJDBC.saveUser("Michael", "Batio", (byte) 66);

            List<User> userArrayList = userDaoJDBC.getAllUsers();
            for (User user : userArrayList) {
                System.out.println(user.toString());
            }

            userDaoJDBC.cleanUsersTable();

            userDaoJDBC.dropUsersTable();

        } catch (SQLException e) {

        }

    }
}
