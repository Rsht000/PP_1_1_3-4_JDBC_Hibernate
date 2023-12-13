package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = " CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY AUTO_INCREMENT," +
                " UserName VARCHAR(55),"
                + "LastName VARCHAR(55),"
                + "AGE TINYINT)";
        try ( Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
            System.out.println("Has been created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String deleteTable = " DROP TABLE if exists Users ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (UserName, LastName, AGE) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
            preparedStatement1.setString(1, name);
            preparedStatement1.setString(2, lastName);
            preparedStatement1.setByte(3, age);
            int affectedRows = preparedStatement1.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User с именем: " +
                        name + " добавлен в базами.");
            }

        } catch (SQLException E) {
            E.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM Users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users ";
        try (Statement statement1 = connection.createStatement()) {
            ResultSet resultSet = statement1.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("UserName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("TRUNCATE Users ")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
