package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/databasefokatatask?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String username = "root";
    private static final String password = "abcd1";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Соединение с базой данных установлено");
            return connection;
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке подключения к БД\n" + e.getMessage());
        }
        return null;
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/databasefokatatask?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "abcd1");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                settings.put(Environment.SHOW_SQL, "false");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Соединение с базой данных установлено");
            } catch (Exception e) {
                System.out.println("Ошибка при попытке подключения к БД\n" + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
