package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() throws SQLException {

    }

    private final Statement statement = Util.getConnection().createStatement();

    public void createUsersTable() {
        try {
            final String commandsCreateTable = "CREATE TABLE IF NOT EXISTS databasefokatatask.users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "lastname VARCHAR(45) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            statement.executeUpdate(commandsCreateTable);
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке создать таблицу\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            final String commandsDropTable = "DROP TABLE IF EXISTS databasefokatatask.users";
            statement.executeUpdate(commandsDropTable);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке удалить таблицу\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            final String commandsInsertTable = String.format("INSERT INTO databasefokatatask.users (name, lastName, age) VALUES ('%s', '%s', %d)", name, lastName, age);
            statement.execute(commandsInsertTable);
            System.out.printf("Пользователь с именем %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке добавить пользователя \n" + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            final String commandsDeleteTable = String.format("DELETE FROM databasefokatatask.users WHERE id = %d", id);
            statement.execute(commandsDeleteTable);
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке удалить пользователя \n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            final String commandsSelectTable = "SELECT * FROM databasefokatatask.users";
            ResultSet resultSet = statement.executeQuery(commandsSelectTable);
            while(resultSet.next()){
                userList.add(new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке получить данные из БД\n" + e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            final String commandsCleanTable = "TRUNCATE TABLE databasefokatatask.users";
            statement.executeUpdate(commandsCleanTable);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке очистить таблицу\n" + e.getMessage());
        }

    }
}
