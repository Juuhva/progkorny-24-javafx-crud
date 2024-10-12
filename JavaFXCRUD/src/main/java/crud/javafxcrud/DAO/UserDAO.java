package crud.javafxcrud.DAO;

import DBConnection.DBConnection;
import crud.javafxcrud.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String addUser = "INSERT INTO users (username, password, name) VALUES (?, ?, ?)";
    private static final String findUserById = "SELECT id, username, password, name FROM users WHERE id = ?";
    private static final String findAllUsers = "SELECT * FROM users";
    private static final String deletUser = "DELETE FROM users WHERE id = ?";
    private static final String updateUser = "UPDATE users SET username = ?, password = ?, name = ? WHERE id = ?";
    private static final String loginUser = "SELECT id, username, password FROM users WHERE username = ? AND password = ?";

    public void addUser(User user) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addUser)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.executeUpdate();
        }
    }

    public User findUserById(int id) throws SQLException {
        User user = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findUserById)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        }
        return user;
    }

    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllUsers)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                User user = new User(username, password, name);
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deletUser)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateUser)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean loginUser(String username, String password) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(loginUser)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        }
    }
}
