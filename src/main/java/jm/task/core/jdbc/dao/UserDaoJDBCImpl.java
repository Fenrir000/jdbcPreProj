package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);
            statement.execute("CREATE TABLE IF NOT EXISTS User(id BIGINT AUTO_INCREMENT PRIMARY key,name varchar(255), lastname varchar(255), age int)");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("DROP TABLE IF EXISTS User");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "insert into users(name,lastname,age) values(?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            connection.commit();
            System.out.println("User с именем "+ name+" добавлен в таблицу");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    public void removeUserById(long id) {
        String remove = "delete from  users where id =?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, (int) id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                list.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("truncate TABLE Users");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
