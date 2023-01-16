package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() { //ddl запрос
        final String COMANDS = "CREATE TABLE IF NOT EXISTS databasefokatatask.users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(45) NOT NULL, " +
                "lastname VARCHAR(45) NOT NULL, " +
                "age TINYINT NOT NULL)";

        try (Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate(COMANDS);

            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке создать таблицу\n" + e.getMessage());
        }
    }

    public void dropUsersTable() { //ddl запрос
        final String COMANDS = "DROP TABLE IF EXISTS databasefokatatask.users";

        try (Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate(COMANDS);

            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке удалить таблицу\n" + e.getMessage());
        }
    }

    public void cleanUsersTable() { //ddl запрос
        final String COMANDS = "TRUNCATE TABLE databasefokatatask.users";
        try (Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate(COMANDS);

            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке очистить таблицу\n" + e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) { //dml запрос
        final String COMANDS = "INSERT INTO databasefokatatask.users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(COMANDS);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.printf("Пользователь с именем %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке добавить пользователя \n" + e.getMessage());
        }
    }

    public void removeUserById(long id) { //dml запрос
        final String COMANDS = "DELETE FROM databasefokatatask.users WHERE id = ?";

        try (Connection connection = Util.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(COMANDS);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке удалить пользователя \n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() { //dml запрос
        List<User> userList = new ArrayList<>();
        final String COMANDS = "SELECT * FROM databasefokatatask.users";

        try (Connection connection = Util.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(COMANDS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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

}
