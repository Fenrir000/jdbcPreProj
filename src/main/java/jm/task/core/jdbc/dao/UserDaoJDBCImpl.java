package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {


            statement.execute("CREATE TABLE IF NOT EXISTS Users(id BIGINT AUTO_INCREMENT PRIMARY key,name varchar(255), lastname varchar(255), age int)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "insert into users(name,lastname,age) values(?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String remove = "delete from  users where id =?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {

            preparedStatement.setInt(1, (int) id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {

                list.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("truncate TABLE Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
