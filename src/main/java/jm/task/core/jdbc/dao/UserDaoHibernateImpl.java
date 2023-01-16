package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.criteria.CriteriaQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE databasefokatatask.users (id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL,lastname VARCHAR(45) NOT NULL," +
                    "age INT NOT NULL, PRIMARY KEY (id)) " +
                    "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8";
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке создать таблицу \n" + e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() { //ddl запрос
        Transaction transaction;
        final String COMANDS = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(COMANDS).executeUpdate();

            transaction.commit();
            System.out.println("Таблица удаленна");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке удалить таблицу \n" + e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() { //ddl запрос
        Transaction transaction;
        final String COMANDS = "DELETE User";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createQuery(COMANDS).executeUpdate();

            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке очистить таблицу \n" + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) { //dml запрос
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
            System.out.printf("Пользователь с именем %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при попытке добавить пользователя \n" + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) { //dml запрос
        Transaction transaction = null;
        final String nativeCOMANDS = String.format("DELETE FROM users WHERE id = %s", id);
        final String hqlCOMANDS = "DELETE User WHERE id = :id";

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

//            session.remove(session.load(User.class,id)); // Criteria API
//            session.createNativeQuery(nativeCOMANDS).executeUpdate(); // Native SQL
            session.createQuery(hqlCOMANDS).setParameter("id", id).executeUpdate(); //HQL

            transaction.commit();

            System.out.println("Пользователь удален");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при попытке удалить пользователя \n" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() { //dml запрос
        final String hqlCOMANDS = "from User";
        try (Session session = Util.getSessionFactory().openSession()) {
//            return session.createCriteria(User.class).list(); // Criteria API
            return session.createQuery(hqlCOMANDS, User.class).list(); //HQL
        }
    }


}
